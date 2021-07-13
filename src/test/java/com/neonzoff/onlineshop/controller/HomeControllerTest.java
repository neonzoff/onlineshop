package com.neonzoff.onlineshop.controller;

import com.neonzoff.onlineshop.dao.ProductRepository;
import com.neonzoff.onlineshop.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
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
@WithUserDetails(value = "user")
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private HomeController homeController;

    @Test
    void contextLoads() {
        assertThat(homeController).isNotNull();
    }

    @Test
    void getHome() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(authenticated());
    }

    @Test
    void getProduct() throws Exception {
        final Product product = productRepository.findAll().get(0);
        final String ID_PRODUCT = String.valueOf(product.getId());

        this.mockMvc.perform(get("/product/" + ID_PRODUCT))
                .andDo(print())
                .andExpect(content().string(containsString(product.getName())));
    }


    @Test
    void userProfile() throws Exception {
        this.mockMvc.perform(get("/profile"))
                .andDo(print())
                .andExpect(content().string(containsString("Профиль пользователя")));
    }
}