package com.epam.learn.controllers;

import com.epam.learn.storage.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest  {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetUsers() throws Exception {

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/users");

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetUserSuccess() throws Exception {

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/users/{id}", 1);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetUserNotSuccess() throws Exception {

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/users/{id}", 7);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.view().name("error"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCreateUser() throws Exception {

        UserEntity user = new UserEntity();
        user.setEmail("User Name");
        user.setEmail("useremail@gmail.com");

        MockHttpServletRequestBuilder builder = patch("/users/{id}/update", 3)
                .flashAttr("user", user);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.redirectedUrl("/users"));
    }

    @Test
    public void testUpdateUserSuccess() throws Exception {

        UserEntity user = new UserEntity();
        user.setId(3);
        user.setEmail("Name");
        user.setEmail("email@gmail.com");

        MockHttpServletRequestBuilder builder = patch("/users/{id}/update", 3)
                .flashAttr("user", user);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.redirectedUrl("/users"));
    }

    @Test
    public void testUpdateUserNotSuccess() throws Exception {

        UserEntity user = new UserEntity();
        user.setId(6);
        user.setName("Name");
        user.setEmail("email@gmail.com");

        MockHttpServletRequestBuilder builder = patch("/users/{id}/update", 6)
                .flashAttr("user", user);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.view().name("error"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteUserSuccess() throws Exception {

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .delete("/users/{id}/delete", 1);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.redirectedUrl("/users"));
    }

    @Test
    public void testDeleteUserNotSuccess() throws Exception {

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .delete("/users/{id}/delete", 7);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.view().name("error"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}