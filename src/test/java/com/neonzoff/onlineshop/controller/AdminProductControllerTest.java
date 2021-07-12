package com.neonzoff.onlineshop.controller;

import com.neonzoff.onlineshop.dao.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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
class AdminProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AdminProductController adminProductController;

    @Test
    void contextLoads() {
        assertThat(adminProductController).isNotNull();
    }

    @Test
    void products() throws Exception {
        this.mockMvc.perform(get("/admin/product"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(content().string(containsString("Список товаров")))
                .andExpect(content().string(containsString(String.valueOf(productRepository.findAll().get(0).getId()))))
                .andExpect(content().string(containsString(String.valueOf(productRepository.findAll().get(1).getId()))));
    }

    @Test
    void editProduct() throws Exception {
        this.mockMvc.perform(get("/admin/product/edit/" + productRepository.findAll().get(0).getId()))
                .andDo(print())
                .andExpect(content().string(containsString(productRepository.findAll().get(0).getName())))
                .andExpect(content().string(containsString(productRepository.findAll().get(0).getDescription())))
                .andExpect(content().string(containsString(String.valueOf(productRepository.findAll().get(0).getPrice()))));
    }
}