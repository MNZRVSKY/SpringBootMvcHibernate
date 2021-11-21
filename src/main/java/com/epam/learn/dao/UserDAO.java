package com.epam.learn.dao;

import com.epam.learn.storage.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserDAO extends CrudRepository<UserEntity, Integer> {

}
