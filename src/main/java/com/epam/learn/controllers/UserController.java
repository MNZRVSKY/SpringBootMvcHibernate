package com.epam.learn.controllers;

import com.epam.learn.exception.UserNotFoundException;
import com.epam.learn.services.BookingService;
import com.epam.learn.storage.UserEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class.getName());

    private BookingService bookingService;

    @Autowired
    public UserController(BookingService bookingService) {

        this.bookingService = bookingService;
    }

    @GetMapping()
    public String getUsers(Model model) {

        LOGGER.debug("Get all users");

        List<UserEntity> users = bookingService.getAllUsers();

        model.addAttribute("users", users);

        return "users/index";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") int id, Model model) throws UserNotFoundException {

        LOGGER.debug("Get user with id: " + id);

        model.addAttribute("user", bookingService.getUserById(id));

        return "users/show";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") UserEntity user) {

        LOGGER.debug("Setup user");

        return "users/new";
    }

    @PostMapping("")
    public String createUser(@ModelAttribute("user") UserEntity user) {

        LOGGER.debug("Create user");

        bookingService.createUser(user);

        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String editUser(Model model, @PathVariable("id") int id) throws UserNotFoundException {

        LOGGER.debug("Prepare user for update with id: " + id);

        model.addAttribute("user", bookingService.getUserById(id));

        return "users/edit";
    }

    @PatchMapping("/{id}/update")
    public String updateUser(@ModelAttribute("user") UserEntity user, @PathVariable("id") int id) throws UserNotFoundException {

        LOGGER.debug("Update user with id: " + id);

        bookingService.updateUser(user);

        return "redirect:/users";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") int id) throws UserNotFoundException {

        LOGGER.debug("Delete user with id: " + id);

        bookingService.deleteUser(id);

        return "redirect:/users";
    }
}
