package com.epam.learn.controllers;

import com.epam.learn.exception.EventNotFoundException;
import com.epam.learn.services.BookingService;
import com.epam.learn.storage.EventEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {

    private static final Logger LOGGER = LogManager.getLogger(EventController.class.getName());

    private BookingService bookingService;

    @Autowired
    public EventController(BookingService bookingService) {

        this.bookingService = bookingService;
    }

    @GetMapping()
    public String getEvents(Model model) {

        List<EventEntity> events = bookingService.getAllEvents();

        model.addAttribute("events", events);

        LOGGER.debug("getEvents");

        return "events/index";
    }

    @GetMapping("/{id}")
    public String getEvent(@PathVariable("id") int id, Model model) throws EventNotFoundException {

        LOGGER.debug("getEvents with id: " + id);

        model.addAttribute("event", bookingService.getEventById(id));

        return "events/show";
    }

    @GetMapping("/new")
    public String newEvent(@ModelAttribute("event") EventEntity event) {

        LOGGER.debug("Setup new event ");

        return "events/new";
    }

    @PostMapping("")
    public String createEvent(@ModelAttribute("event") EventEntity event) {

        LOGGER.debug("Create new event");

        bookingService.createEvent(event);

        return "redirect:/events";
    }

    @GetMapping("/{id}/edit")
    public String editEvent(Model model, @PathVariable("id") int id) throws EventNotFoundException {

        LOGGER.debug("Edit event with id: " + id);

        model.addAttribute("event", bookingService.getEventById(id));

        return "events/edit";
    }

    @PatchMapping("/{id}/update")
    public String updateEvent(@ModelAttribute("event") EventEntity event, @PathVariable("id") int id) throws EventNotFoundException {

        LOGGER.debug("Update event with id: " + id);

        bookingService.updateEvent(event);

        return "redirect:/events";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteEvent(@PathVariable("id") int id) throws EventNotFoundException {

        LOGGER.debug("Delete event with id: " + id);

        bookingService.deleteEvent(id);

        return "redirect:/events";
    }
}
