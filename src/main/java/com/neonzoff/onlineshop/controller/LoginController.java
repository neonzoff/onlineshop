package com.neonzoff.onlineshop.controller;

import com.neonzoff.onlineshop.dto.UserForm;
import com.neonzoff.onlineshop.model.Role;
import com.neonzoff.onlineshop.model.UserModel;
import com.neonzoff.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tseplyaev Dmitry
 */
@Controller
public class LoginController {
    private UserService service;

    @Autowired
    public LoginController(@Qualifier(value = "userServiceImpl") UserService service) {
        this.service = service;
    }

    @GetMapping("/afterLogin")
    public String afterLogin(HttpServletRequest request) {
        if (request.isUserInRole(Role.ADMIN.name())) {
            return "redirect:/admin";
        }
        return "redirect:/";
    }

    @GetMapping("/registration")
    public String getRegistration(Model model) {
        model.addAttribute("userForm", new UserModel());
        return "registration";
    }

    @PostMapping("/registration")
    public String userRegistration(@ModelAttribute("userForm") UserForm userForm) {

        UserModel user = new UserModel();
        user.setUsername(userForm.getUsername());
        user.setPassword(userForm.getPassword());
        user.setName(userForm.getName());
        user.setSurname(userForm.getSurname());
        user.setMiddleName(userForm.getMiddlename());
        user.setAddress(userForm.getAddress());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setBalance(0);
        user.setRole(Role.USER);
        service.createUser(user);
        return "redirect:/login";
    }
}
