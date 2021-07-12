package com.neonzoff.onlineshop.controller;

import com.neonzoff.onlineshop.dao.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * @author Tseplyaev Dmitry
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WithUserDetails(value = "admin")
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminController adminController;

    @Test
    void contextLoads() {
        assertThat(adminController).isNotNull();
    }

    @Test
    void getAdminPanel() throws Exception {
        this.mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(content().string(containsString("Панель управления")));
    }

    @Test
    void getUsers() throws Exception {
        this.mockMvc.perform(get("/admin/user"))
                .andDo(print())
                .andExpect(content().string(containsString(userRepository.findAll().get(0).getName())))
                .andExpect(content().string(containsString(userRepository.findAll().get(1).getName())))
                .andExpect(content().string(containsString(userRepository.findAll().get(2).getName())));
    }

    /*
    @Test
    void getUser() throws Exception {
        final int ID = 54;

        this.mockMvc.perform(get("/admin/user/" + ID))
                .andDo(print())
                .andExpect(content().string(containsString(userRepository.findById(ID).get().getName())));
    }
    */
}