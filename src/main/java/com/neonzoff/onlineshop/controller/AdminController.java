package com.neonzoff.onlineshop.controller;

import com.neonzoff.onlineshop.dao.UserRepository;
import com.neonzoff.onlineshop.model.UserModel;
import com.neonzoff.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Tseplyaev Dmitry
 */
@Controller
@RequestMapping("/admin")
@PreAuthorize(value = "hasAuthority('ADMIN')")
public class AdminController {

    @Autowired
    private UserRepository repository;
    @Autowired
    private UserService service;


    @GetMapping
    public String getAdminPanel() {
        return "admin_panel";
    }

    @GetMapping("/user")
    public ModelAndView getUsers(@RequestParam("page") Optional<Integer> page,
                                 @RequestParam("size") Optional<Integer> size
    ) {
        ModelAndView modelAndView = new ModelAndView("all_users");
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(15);

        Page<UserModel> userPage = service.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        modelAndView.addObject("userPage", userPage);

        int totalPages = userPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }

        return modelAndView;
    }

    @GetMapping("/user/{id_user}")
    public String getUser() {
        return "user/{id_user}";
    }


    @GetMapping("/orders")
    public String getOrders() {
        return "orders";
    }

    @GetMapping("/orders/{id_order}")
    public String getOrder() {
        return "order/{id_order}";
    }

    @PostMapping("/orders/{id_order}")
    public String changeOrderStatus() {
        return "order/{id_order}";
    }


}
