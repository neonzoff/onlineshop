package com.neonzoff.onlineshop.service;

import com.neonzoff.onlineshop.dao.UserRepository;
import com.neonzoff.onlineshop.model.UserModel;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Tseplyaev Dmitry
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class UserServiceImplTest {
    @Autowired
    private UserServiceImpl userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    void createUser() {
        UserModel userModel = new UserModel();

        boolean isUserCreated = userService.createUser(userModel);

        assertTrue(isUserCreated);

        Mockito.verify(userRepository, Mockito.times(1)).save(userModel);
    }

    @Test
    void createExistingUser() {
        final String USERNAME = "dmitry";

        UserModel userModel = new UserModel();
        userModel.setUsername(USERNAME);
        Mockito.doReturn(Optional.of(new UserModel()))
                .when(userRepository)
                .findByUsername(USERNAME);

        boolean isUserCreated = userService.createUser(userModel);

        assertFalse(isUserCreated);

        Mockito.verify(userRepository, Mockito.times(0)).save(userModel);

    }


    @Test
    void allUsers() {
        List<UserModel> users = userRepository.findAll();
        Mockito.verify(userRepository, Mockito.times(3)).findAll();
    }

    @Test
    void loadUserByUsername() {
        final String USERNAME = "dmitry";
        UserModel user = new UserModel();
        user.setUsername("Dmitry");

        Mockito.doReturn(Optional.of(user))
                .when(userRepository)
                .findByUsername(USERNAME);
        String username = userService.loadUserByUsername(USERNAME).getUsername();


        assertNotNull(username);
        assertEquals("Dmitry", username);

        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(USERNAME);
    }
}