package com.epam.learn.services;

import com.epam.learn.dao.UserDAO;
import com.epam.learn.exception.UserNotFoundException;
import com.epam.learn.storage.UserEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserService.class.getName());

    private final UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<UserEntity> getAllUsers() {

        LOGGER.debug("getAllUsers");

        List<UserEntity> users = new ArrayList<>();
        userDAO.findAll().forEach(users::add);

        return users;
    }

    public UserEntity getUserById(int userId) {

        LOGGER.debug("User with id << " + userId + " >> founded!");

        UserEntity user = userDAO.findById(userId).orElse(null);;

        if (user == null) {
            throw new UserNotFoundException(userId);
        }

        return user;
    }

    public UserEntity getUserByEmail(String email) {

        LOGGER.debug("getUserByEmail << " + email + " >>");

        List<UserEntity> users = new ArrayList<>();
        userDAO.findAll().forEach(users::add);

        return users.stream()
                .filter(user->user.getEmail().equals(email))
                .findAny()
                .orElse(null);
    }

    public List<UserEntity> getUsersByName(String name, int pageSize, int pageNum) {

        LOGGER.debug("getUsersByName << " + name + " >> pageSize << "
                + pageSize + " >> pageNum << " + pageNum + " >>");

        List<UserEntity> usersByName = getAllUsers()
                .stream()
                .filter(user -> user.getName().equals(name))
                .collect(Collectors.toList());

        int beginIndex = pageSize * pageNum ;

        if (usersByName.size() == 0 || usersByName.size() < beginIndex) {
            LOGGER.debug("Didn't find users with name << " + name + " >>");
            return new ArrayList<>();
        }

        int endIndex = beginIndex + pageSize;

        if(endIndex > usersByName.size()) {
            endIndex = usersByName.size();
        }

        LOGGER.debug("Users with name << " + name + " >> founded!");
        return usersByName.subList(beginIndex, endIndex);
    }

    public UserEntity createUser(@NotNull UserEntity user) {

        LOGGER.debug("createUser << " + user.getName() + " >>");

        return userDAO.save(user);
    }

    public UserEntity updateUser(@NotNull UserEntity user) {

        LOGGER.debug("updateUser << " + user.getId() + " >>");

        UserEntity existedtedUser = userDAO.findById(user.getId()).orElse(null);

        if (existedtedUser == null) {
            throw new UserNotFoundException(user.getId());
        }

        if(existedtedUser.getId() == user.getId()) {
            existedtedUser.setName(user.getName());
            existedtedUser.setEmail(user.getEmail());
        }

        UserEntity updatedUser = userDAO.save(existedtedUser);

        return updatedUser;
    }

    public boolean deleteUser(int userId) {

        LOGGER.debug("deleteUser << " + userId + " >>");

        if(!userDAO.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }

        userDAO.deleteById(userId);

        return true;
    }
}
