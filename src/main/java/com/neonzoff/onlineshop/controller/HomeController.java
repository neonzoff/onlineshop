package com.neonzoff.onlineshop.controller;

import com.neonzoff.onlineshop.dao.ProductRepository;
import com.neonzoff.onlineshop.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author Tseplyaev Dmitry
 */
@Controller
public class HomeController {
    @Autowired
    private ProductRepository repository;

    @RequestMapping("/")
    public ModelAndView getHome() {
        ModelAndView modelAndView = new ModelAndView("index");
        List<Product> productList = repository.findAll();
        modelAndView.addObject("productList", productList);
        return modelAndView;
    }
}
