package com.epam.learn.controllers;

import com.epam.learn.storage.EventEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetEvents() throws Exception {

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/events");

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testGetEventsByIdSuccess() throws Exception {

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/events/{id}", 1);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testGetEventThrowEventNotFoundException() throws Exception {

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/events/{id}", 55);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.view().name("error"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCreateEvent() throws Exception {

        EventEntity event = new EventEntity();
        event.setTitle("Title event");
        event.setDate("11-22-2020 10:00");

        MockHttpServletRequestBuilder builder = post("/events")
                .flashAttr("event", event);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.redirectedUrl("/events"));
    }

    @Test
    public void testUpdateEventSuccess() throws Exception {

        EventEntity event = new EventEntity();
        event.setId(3);
        event.setTitle("Title event");
        event.setDate("11-22-2020 10:00");

        MockHttpServletRequestBuilder builder = patch("/events/{id}/update", 3)
                .flashAttr("event", event);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.redirectedUrl("/events"));
    }

    @Test
    public void testUpdateEventNotSuccess() throws Exception {

        EventEntity event = new EventEntity();
        event.setId(77);
        event.setTitle("Title event");
        event.setDate("11-22-2020 10:00");

        MockHttpServletRequestBuilder builder = patch("/events/{id}/update", event.getId())
                .flashAttr("event", event);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.view().name("error"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteEventByIdSuccess() throws Exception {

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .delete("/events/{id}/delete", 3);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.redirectedUrl("/events"));

    }

    @Test
    public void testDeleteEventByIdNotSuccess() throws Exception {

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .delete("/events/{id}/delete", 7);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.view().name("error"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
