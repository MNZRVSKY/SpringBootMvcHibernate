package com.epam.learn.facade;

import com.epam.learn.exception.EventNotFoundException;
import com.epam.learn.exception.TicketNotFoundException;
import com.epam.learn.exception.UserNotFoundException;
import com.epam.learn.model.TicketModel;
import com.epam.learn.storage.EventEntity;
import com.epam.learn.storage.TicketEntity;
import com.epam.learn.storage.UserEntity;

import java.util.Date;
import java.util.List;

/**
 * Groups together all operations related to tickets booking.
 * Created by maksym_govorischev.
 */
public interface BookingFacade {

    /**
     * Gets event by its id.
     * @return Event.
     */
    EventEntity getEventById(int eventId) throws EventNotFoundException;

    /**
     * Get list of events by matching title. Title is matched using 'contains' approach.
     * In case nothing was found, empty list is returned.
     * @param title Event title or it's part.
     * @param pageSize Pagination param. Number of events to return on a page.
     * @param pageNum Pagination param. Number of the page to return. Starts from 1.
     * @return List of events.
     */
    List<EventEntity> getEventsByTitle(String title, int pageSize, int pageNum);

    /**
     * Get list of events for specified day.
     * In case nothing was found, empty list is returned.
     * @param day Date object from which day information is extracted.
     * @param pageSize Pagination param. Number of events to return on a page.
     * @param pageNum Pagination param. Number of the page to return. Starts from 1.
     * @return List of events.
     */
    List<EventEntity> getEventsForDay(Date day, int pageSize, int pageNum);

    /**
     * Creates new event. Event id should be auto-generated.
     * @param event Event data.
     * @return Created Event object.
     */
    EventEntity createEvent(EventEntity event);

    /**
     * Updates event using given data.
     * @param event Event data for update. Should have id set.
     * @return Updated Event object.
     */
    EventEntity updateEvent(EventEntity event) throws EventNotFoundException;

    /**
     * Deletes event by its id.
     * @param eventId Event id.
     * @return Flag that shows whether event has been deleted.
     */
    boolean deleteEvent(int eventId) throws EventNotFoundException;

    /**
     * Gets user by its id.
     * @return User.
     */
    UserEntity getUserById(int userId) throws UserNotFoundException;

    /**
     * Gets user by its email. Email is strictly matched.
     * @return User.
     */
    UserEntity getUserByEmail(String email);

    /**
     * Get list of users by matching name. Name is matched using 'contains' approach.
     * In case nothing was found, empty list is returned.
     * @param name Users name or it's part.
     * @param pageSize Pagination param. Number of users to return on a page.
     * @param pageNum Pagination param. Number of the page to return. Starts from 1.
     * @return List of users.
     */
    List<UserEntity> getUsersByName(String name, int pageSize, int pageNum);

    /**
     * Creates new user. User id should be auto-generated.
     * @param user User data.
     * @return Created User object.
     */
    UserEntity createUser(UserEntity user);

    /**
     * Updates user using given data.
     * @param user User data for update. Should have id set.
     * @return Updated User object.
     */
    UserEntity updateUser(UserEntity user) throws UserNotFoundException;

    /**
     * Deletes user by its id.
     * @param userId User id.
     * @return Flag that shows whether user has been deleted.
     */
    boolean deleteUser(int userId) throws UserNotFoundException;

    /**
     * Book ticket for a specified event on behalf of specified user.
     * @param userId User Id.
     * @param eventId Event Id.
     * @param place Place number.
     * @param category Service category.
     * @return Booked ticket object.
     * @throws IllegalStateException if this place has already been booked.
     */
    TicketEntity bookTicket(int userId, int eventId, int place, TicketModel.Category category) throws UserNotFoundException, EventNotFoundException;

    /**
     * Get all booked tickets for specified user. Tickets should be sorted by event date in descending order.
     * @param userId User
     * @param pageSize Pagination param. Number of tickets to return on a page.
     * @param pageNum Pagination param. Number of the page to return. Starts from 1.
     * @return List of Ticket objects.
     */
    List<TicketEntity> getBookedTicketsByUserId(int userId, int pageSize, int pageNum);

    /**
     * Get all booked tickets for specified event. Tickets should be sorted in by user email in ascending order.
     * @param eventId Event
     * @param pageSize Pagination param. Number of tickets to return on a page.
     * @param pageNum Pagination param. Number of the page to return. Starts from 1.
     * @return List of Ticket objects.
     */
    List<TicketEntity> getBookedTicketsByEventId(int eventId, int pageSize, int pageNum);

    /**
     * Cancel ticket with a specified id.
     * @param ticketId Ticket id.
     * @return Flag whether anything has been canceled.
     */
    boolean cancelTicket(int ticketId) throws TicketNotFoundException;

}
