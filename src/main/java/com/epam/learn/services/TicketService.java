package com.epam.learn.services;

import com.epam.learn.dao.TicketDAO;
import com.epam.learn.exception.TicketNotFoundException;
import com.epam.learn.model.TicketModel;
import com.epam.learn.storage.EventEntity;
import com.epam.learn.storage.TicketEntity;
import com.epam.learn.storage.UserEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TicketService {

    private static final Logger LOGGER = LogManager.getLogger(TicketService.class.getName());

    private final TicketDAO ticketDAO;

    @Autowired
    public TicketService(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    public TicketEntity bookTicket(UserEntity user, EventEntity event, int place, TicketModel.Category category) {

        LOGGER.debug("bookTicket for user id << " + user.getId() + " >>");

        TicketEntity ticket = new TicketEntity();
        ticket.setCategory(category);
        ticket.setPlace(place);
        ticket.setUser(user);
        ticket.setEvent(event);

        return ticketDAO.save(ticket);
    }

    public List<TicketEntity> getAllBookedTickets() {

        LOGGER.debug("getAllBookedTickets");

        List<TicketEntity> tickets = new ArrayList<>();
        ticketDAO.findAll().forEach(tickets::add);

        return tickets;
    }

    public List<TicketEntity> getBookedTicketsByUserId(int userId, int pageSize, int pageNum) {

        LOGGER.debug("getBookedTickets User id << " + userId + " >>");

        List<TicketEntity> tickets = new ArrayList<>();
        ticketDAO.findAll().forEach(tickets::add);

        return getTicketsByPage(tickets, pageSize, pageNum);
    }

    public List<TicketEntity> getBookedTicketsByEventId(int eventId, int pageSize, int pageNum) {

        LOGGER.debug("getBookedTickets Event id << " + eventId + " >>");

        List<TicketEntity> tickets = new ArrayList<>();
        ticketDAO.findAll().forEach(tickets::add);

        return getTicketsByPage(tickets, pageSize, pageNum);
    }

    public boolean cancelTicket(int ticketId) {

        LOGGER.debug("cancelTicket id:  << " + ticketId + " >>");

        if(!ticketDAO.existsById(ticketId)) {
            throw new TicketNotFoundException(ticketId);
        }

        ticketDAO.deleteById(ticketId);

        return ticketDAO.findById(ticketId).orElse(null) == null;
    }


    private List<TicketEntity> getTicketsByPage(List<TicketEntity> pageTickets, int pageSize, int pageNum) {

        if (pageTickets.size() == 0 ||
                pageTickets.size() < pageSize * pageNum) {
            return new ArrayList<>();
        }

        int endIndex = pageSize * pageNum + pageSize;

        if (endIndex > pageTickets.size()) {
            endIndex = pageTickets.size();
        }

        return pageTickets.subList(pageSize * pageNum, endIndex);
    }
}
