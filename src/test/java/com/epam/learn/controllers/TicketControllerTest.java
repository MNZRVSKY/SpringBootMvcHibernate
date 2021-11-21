package com.epam.learn.controllers;

import com.epam.learn.model.TicketModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetTickets() throws Exception {

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/tickets");

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetTicketByUserIdSuccess() throws Exception {

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/tickets/foruser/{userId}/{pageSize}/{pageNum}", 1, 6, 0);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetTicketByEventId() throws Exception {

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/tickets/forevent/{eventId}/{pageSize}/{pageNum}", 1, 6, 0);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testBookNewTicketSuccess() throws Exception {

        TicketModel ticket = new TicketModel();
        ticket.setEventId(2);
        ticket.setUserId(1);
        ticket.setCategory(TicketModel.Category.STANDARD);
        ticket.setPlace(88);

        MockHttpServletRequestBuilder builder = post("/tickets")
                .flashAttr("ticket", ticket);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.redirectedUrl("/tickets"));
    }

    @Test
    public void testBookNewTicketWrongUser() throws Exception {

        TicketModel ticket = new TicketModel();
        ticket.setEventId(2);
        ticket.setUserId(77);
        ticket.setCategory(TicketModel.Category.STANDARD);
        ticket.setPlace(123);

        MockHttpServletRequestBuilder builder = post("/tickets")
                .flashAttr("ticket", ticket);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.view().name("error"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testBookNewTicketWrongEvent() throws Exception {

        TicketModel ticket = new TicketModel();
        ticket.setEventId(77);
        ticket.setUserId(1);
        ticket.setCategory(TicketModel.Category.STANDARD);
        ticket.setPlace(231);

        MockHttpServletRequestBuilder builder = post("/tickets")
                .flashAttr("ticket", ticket);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.view().name("error"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testBookNewTicketWrongPlace() throws Exception {

        TicketModel ticket = new TicketModel();
        ticket.setEventId(1);
        ticket.setUserId(1);
        ticket.setCategory(TicketModel.Category.STANDARD);
        ticket.setPlace(1);

        MockHttpServletRequestBuilder builder = post("/tickets")
                .flashAttr("ticket", ticket);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.view().name("error"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteTicket() throws Exception {

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .delete("/tickets/{id}/delete", 2);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.redirectedUrl("/tickets"));
    }

    @Test
    public void testDeleteTicketNotSuccess() throws Exception {

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .delete("/tickets/{id}/delete", 77);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.view().name("error"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}