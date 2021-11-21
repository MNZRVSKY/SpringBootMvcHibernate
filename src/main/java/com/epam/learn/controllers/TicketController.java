package com.epam.learn.controllers;

import com.epam.learn.exception.EventNotFoundException;
import com.epam.learn.exception.TicketNotFoundException;
import com.epam.learn.exception.UserNotFoundException;
import com.epam.learn.helpers.TicketsPdfExporter;
import com.epam.learn.model.TicketModel;
import com.epam.learn.services.BookingService;
import com.epam.learn.storage.TicketEntity;
import com.lowagie.text.DocumentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private static final Logger LOGGER = LogManager.getLogger(TicketController.class.getName());

    private BookingService bookingService;

    @Autowired
    public TicketController(BookingService bookingService) {

        this.bookingService = bookingService;
    }

    @GetMapping()
    public String getTickets(Model model) {

        LOGGER.debug("Get all tickets");

        List<TicketEntity> tickets = bookingService.getAllBookedTickets();

        model.addAttribute("tickets", tickets);

        return "tickets/index";
    }

    @GetMapping("/foruser/{userId}/{pageSize}/{pageNum}")
    public String getTicketByUserId(@PathVariable("userId") int userId,
                            @PathVariable("pageSize") int pageSize,
                            @PathVariable("pageNum") int pageNum,
                            Model model) {

        LOGGER.debug("Get ticket with user id: " +  userId);

        List<TicketEntity> bookedTickets = bookingService
                .getBookedTicketsByUserId(userId, pageSize, pageNum);

        model.addAttribute("tickets", bookedTickets);

        return "tickets/index";
    }

    @GetMapping("/forevent/{eventId}/{pageSize}/{pageNum}")
    public String getTicketByEventId(@PathVariable("eventId") int eventId,
                            @PathVariable("pageSize") int pageSize,
                            @PathVariable("pageNum") int pageNum,
                            Model model) {

        LOGGER.debug("Get ticket with event id: " +  eventId);

        List<TicketEntity> bookedTickets = bookingService
                .getBookedTicketsByEventId(eventId, pageSize, pageNum);

        model.addAttribute("tickets", bookedTickets);

        return "tickets/index";
    }

    @GetMapping("/new")
    public String newTicket(@ModelAttribute("ticket") TicketModel ticket) {

        LOGGER.debug("Setup new ticket");

        return "tickets/new";
    }

    @PostMapping("")
    public String bookNewTicket(@ModelAttribute("ticket") TicketModel ticket) throws UserNotFoundException, EventNotFoundException {

        LOGGER.debug("Book new ticket");

        bookingService.bookTicket(ticket.getUserId(), ticket.getEventId(), ticket.getPlace(), ticket.getCategory());

        return "redirect:/tickets";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteTicket(@PathVariable("id") int id) throws TicketNotFoundException {

        LOGGER.debug("Delete ticket");

        bookingService.cancelTicket(id);

        return "redirect:/tickets";
    }

    @GetMapping("/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {

        LOGGER.debug("Export tickets to PDF");

        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=tickets_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        TicketsPdfExporter exporter = new TicketsPdfExporter(bookingService.getAllUsers(),
                                                             bookingService.getAllBookedTickets(),
                                                             bookingService.getAllEvents());
        exporter.export(response);
    }
}
