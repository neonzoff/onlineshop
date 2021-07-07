package com.neonzoff.onlineshop.controller;

import com.neonzoff.onlineshop.dao.ProductRepository;
import com.neonzoff.onlineshop.dao.UserRepository;
import com.neonzoff.onlineshop.model.Product;
import com.neonzoff.onlineshop.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Tseplyaev Dmitry
 */
@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping
    public ModelAndView getHome() {
        ModelAndView modelAndView = new ModelAndView("index");
        List<Product> productList = productRepository.findAll();
        modelAndView.addObject("productList", productList);
        return modelAndView;
    }

    @RequestMapping("/product/{id_product}")
    public ModelAndView getProduct(@PathVariable int id_product) {
        ModelAndView modelAndView = new ModelAndView("product");
        Product product = productRepository.findById(id_product).get();
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @RequestMapping("/profile")
    public ModelAndView userProfile(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("profile");
        UserModel user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();
        modelAndView.addObject("user", user);
        return modelAndView;
    }

}
