package com.epam.learn.services;

import com.epam.learn.exception.EventNotFoundException;
import com.epam.learn.exception.NotEnoughMoneyException;
import com.epam.learn.exception.TicketNotFoundException;
import com.epam.learn.exception.UserNotFoundException;
import com.epam.learn.facade.BookingFacade;
import com.epam.learn.model.TicketModel;
import com.epam.learn.storage.AccountEntity;
import com.epam.learn.storage.EventEntity;
import com.epam.learn.storage.TicketEntity;
import com.epam.learn.storage.UserEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static javax.transaction.Transactional.TxType.NEVER;

@Service
@Transactional
public class BookingService implements BookingFacade {

    private static final Logger LOGGER = LogManager.getLogger(BookingService.class.getName());

    private UserService userService;
    private EventService eventService;
    private TicketService ticketService;
    private AccountService accountService;

    private SessionFactory sessionFactory;

    @Autowired
    public BookingService(UserService userService, EventService eventService,
                          TicketService ticketService, AccountService accountService,
                          SessionFactory sessionFactory) {
        this.userService = userService;
        this.eventService = eventService;
        this.ticketService = ticketService;
        this.accountService = accountService;
        this.sessionFactory = sessionFactory;
    }

    public List<EventEntity> getAllEvents() {

        LOGGER.info("BookingService method: getAllEvents ");

        return eventService.getAllEvents();
    }

    @Override
    public EventEntity getEventById(int eventId) throws EventNotFoundException {

        LOGGER.info("BookingService method: getEventById ");

        return eventService.getEventById(eventId);
    }

    @Override
    public List<EventEntity> getEventsByTitle(String title, int pageSize, int pageNum) {

        LOGGER.info("BookingService method: getEventsByTitle ");

        return eventService.getEventsByTitle(title, pageSize, pageNum);
    }

    @Override
    public List<EventEntity> getEventsForDay(Date day, int pageSize, int pageNum) {

        LOGGER.info("BookingService method: getEventsForDay ");

        return eventService.getEventsForDay(day, pageSize, pageNum);
    }

    @Override
    public EventEntity createEvent(EventEntity event) {

        LOGGER.info("BookingService method: createEvent ");

        return eventService.createEvent(event);
    }

    @Override
    public EventEntity updateEvent(EventEntity event) throws EventNotFoundException {

        LOGGER.info("BookingService method: updateEvent ");

        return eventService.updateEvent(event);
    }

    @Override
    public boolean deleteEvent(int eventId) throws EventNotFoundException {

        LOGGER.info("BookingService method: deleteEvent ");

        return eventService.deleteEvent(eventId);
    }

    public List<UserEntity> getAllUsers() {

        LOGGER.info("BookingService method: getAllUsers ");

        return userService.getAllUsers();
    }

    @Override
    public UserEntity getUserById(int userId) throws UserNotFoundException {

        LOGGER.info("BookingService method: getUserById ");

        return userService.getUserById(userId);
    }

    @Override
    public UserEntity getUserByEmail(String email) {

        LOGGER.info("BookingService method: getUserByEmail ");

        return userService.getUserByEmail(email);
    }

    @Override
    public List<UserEntity> getUsersByName(String name, int pageSize, int pageNum) {

        LOGGER.info("BookingService method: getUsersByName ");

        return userService.getUsersByName(name, pageSize, pageNum);
    }

    @Override
    public UserEntity createUser(UserEntity user) {

        LOGGER.info("BookingService method: createUser ");

        return userService.createUser(user);
    }

    @Override
    public UserEntity updateUser(UserEntity user) throws UserNotFoundException {

        LOGGER.info("BookingService method: updateUser ");

        return userService.updateUser(user);
    }

    @Override
    public boolean deleteUser(int userId) throws UserNotFoundException {

        LOGGER.info("BookingService method: deleteUser ");

        return userService.deleteUser(userId);
    }

    @Override
    @Transactional(NEVER)
    public TicketEntity bookTicket(int userId, int eventId, int place, TicketModel.Category category) {

        LOGGER.info("BookingService method: bookTicket ");

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        UserEntity user = userService.getUserById(userId);
        EventEntity event = eventService.getEventById(eventId);

        if(ticketService.getAllBookedTickets()
                .stream()
                .filter(ticket -> ticket.getEvent().getId() == eventId)
                .filter(ticket -> ticket.getPlace() == place)
                .collect(Collectors.toList())
                .size() > 0) {

            transaction.rollback();

            throw new IllegalStateException("Ticket for this date and this place is already booked");
        }

        AccountEntity account = accountService.getAllAccounts()
                .stream().filter(acc -> acc.getUserId() == userId)
                .findFirst()
                .orElse(null);

        if(account != null) {

            if(account.getUserMoney() < event.getPrice()) {

                transaction.rollback();
                throw new NotEnoughMoneyException(account.getUserId());
            }

            accountService.updateAccount(userId, account.getUserMoney() - event.getPrice());


            TicketEntity ticket = new TicketEntity();
            ticket.setCategory(category);
            ticket.setPlace(place);
            ticket.setUser(user);
            ticket.setEvent(event);

            session.save(ticket);

            transaction.commit();
            session.close();

            return ticket;
        }

        transaction.rollback();

        return null;
    }

    public List<TicketEntity> getAllBookedTickets() {

        LOGGER.info("BookingService method: getAllBookedTickets ");

        return ticketService.getAllBookedTickets();
    }

    @Override
    public List<TicketEntity> getBookedTicketsByUserId(int userId, int pageSize, int pageNum) {

        LOGGER.info("BookingService method: getBookedTickets ");

        return ticketService.getBookedTicketsByUserId(userId, pageSize, pageNum);
    }

    @Override
    public List<TicketEntity> getBookedTicketsByEventId(int eventId, int pageSize, int pageNum) {

        LOGGER.info("BookingService method: getBookedTickets ");

        return ticketService.getBookedTicketsByEventId(eventId, pageSize, pageNum);
    }

    @Override
    public boolean cancelTicket(int ticketId) throws TicketNotFoundException {

        LOGGER.info("BookingService method: cancelTicket ");

        return ticketService.cancelTicket(ticketId);
    }
}
