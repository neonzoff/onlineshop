package com.neonzoff.onlineshop.controller;

import com.neonzoff.onlineshop.dto.UserForm;
import com.neonzoff.onlineshop.model.PrivateAccount;
import com.neonzoff.onlineshop.model.Role;
import com.neonzoff.onlineshop.model.UserModel;
import com.neonzoff.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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


    @GetMapping("/registration")
    public String getRegistration(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "registration";
    }

    @PostMapping("/registration")
    public String userRegistration(@Valid @ModelAttribute("userForm") UserForm userForm, BindingResult bindingResult,
                                   Model model
    ) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMessage.append(error.getDefaultMessage()).append('\n');
            }
            model.addAttribute("errorMessage", errorMessage);
            return "registration";
        } else {
            UserModel user = new UserModel();
            user.setUsername(userForm.getUsername());
            user.setPassword(userForm.getPassword());
            user.setName(userForm.getName());
            user.setSurname(userForm.getSurname());
            user.setMiddleName(userForm.getMiddlename());
            user.setAddress(userForm.getAddress());
            user.setPhoneNumber(userForm.getPhoneNumber());
            user.setRole(Role.USER);
            PrivateAccount privateAccount = new PrivateAccount();
            privateAccount.setUser(user);
            user.setPrivateAccount(privateAccount);
            service.createUser(user);
            return "redirect:/login";
        }
    }
}
