package com.epam.learn.services;

import com.epam.learn.dao.EventDAO;
import com.epam.learn.exception.EventNotFoundException;
import com.epam.learn.storage.EventEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventService {

    private static final Logger LOGGER = LogManager.getLogger(EventService.class.getName());

    private final EventDAO eventDAO;

    @Autowired
    public EventService(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    public List<EventEntity> getAllEvents() {

        LOGGER.debug("getAllEvents");

        List<EventEntity> events = new ArrayList<>();
        eventDAO.findAll().forEach(events::add);

        return events;
    }

    public EventEntity getEventById(int eventId) throws EventNotFoundException {

        LOGGER.debug("getEventById:  << " + eventId + " >>");

        EventEntity eventEntity = eventDAO.findById(eventId).orElse(null);

        if(eventEntity == null) {
            throw new EventNotFoundException(eventId);
        }

        return eventEntity;
    }

    public List<EventEntity> getEventsByTitle(String title, int pageSize, int pageNum) {

        LOGGER.debug("getEventsByTitle << " + title + ">> ");

        List<EventEntity> events = new ArrayList<>();
        eventDAO.findAll().forEach(events::add);

        return getEventsByPage(events.stream()
                .filter(event -> event.getTitle().equals(title))
                .collect(Collectors.toList()), pageSize, pageNum);
    }

    public List<EventEntity> getEventsForDay(Date day, int pageSize, int pageNum) {

        LOGGER.debug("getEventsForDay " + day.toString());

        List<EventEntity> events = new ArrayList<>();
        eventDAO.findAll().forEach(events::add);

        return getEventsByPage(events.stream()
                .filter(event -> event.getDate().equals(day))
                .collect(Collectors.toList()), pageSize, pageNum);
    }

    public EventEntity createEvent(EventEntity event) {

        LOGGER.debug("createEvent id << " + event.getId() + " >>");

        return eventDAO.save(event);
    }

    public EventEntity updateEvent(EventEntity event) throws EventNotFoundException {

        LOGGER.debug("updateEvent id << " + event.getId() + " >>");

        if(!eventDAO.existsById(event.getId())) {
            throw new EventNotFoundException(event.getId());
        }

        EventEntity existsEvent = eventDAO.findById(event.getId()).orElse(null);

        if (existsEvent.getId() == event.getId()) {
            existsEvent.setDate(event.getDate());
            existsEvent.setTitle(event.getTitle());
        }

        return eventDAO.save(existsEvent);
    }

    public boolean deleteEvent(int eventId) {

        LOGGER.debug("deleteEvent id << " + eventId + " >>");

        if(!eventDAO.existsById(eventId)) {
            throw new EventNotFoundException(eventId);
        }

        eventDAO.deleteById(eventId);

        return true;
    }

    private List<EventEntity> getEventsByPage(List<EventEntity> pageEvents, int pageSize, int pageNum) {

        if (pageEvents.size() == 0 ||
                pageEvents.size() < pageSize * pageNum) {
            return new ArrayList<>();
        }

        int endIndex = pageSize * pageNum + pageSize;

        if(endIndex > pageEvents.size()) {
            endIndex = pageEvents.size();
        }

        return pageEvents.subList(pageSize * pageNum, endIndex);
    }
}
