package com.neonzoff.onlineshop.controller;

import com.neonzoff.onlineshop.dao.OrderRepository;
import com.neonzoff.onlineshop.dao.ProductRepository;
import com.neonzoff.onlineshop.dao.UserRepository;
import com.neonzoff.onlineshop.dto.AddToOrderForm;
import com.neonzoff.onlineshop.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

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

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping
    public ModelAndView getHome() {
        ModelAndView modelAndView = new ModelAndView("index");
        List<Product> productList = productRepository.findAll();
        modelAndView.addObject("productList", productList);
        return modelAndView;
    }

    @GetMapping("/product/{id_product}")
    public ModelAndView getProduct(@PathVariable int id_product) {
        ModelAndView modelAndView = new ModelAndView("product");
        Product product = productRepository.findById(id_product).get();
        modelAndView.addObject("product", product);
        modelAndView.addObject("addToOrderForm", new AddToOrderForm());
        return modelAndView;
    }

    @PostMapping("/product/{id_product}")
    public String getProductInOrder(@PathVariable int id_product,
                                    @ModelAttribute("addToOrderForm") AddToOrderForm addToOrderForm,
                                    HttpServletRequest request
    ) {
        UserModel user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();
        OrderModel orderModel;
        if (!(orderRepository.findByUserAndStatusOfOrder(user, StatusOfOrder.NOT_PAID).isPresent())) {
            orderModel = new OrderModel();
            Set<Product> products = orderModel.getProducts();
            products.add(productRepository.findById(id_product).get());
            orderModel.setStatusOfOrder(StatusOfOrder.NOT_PAID);
            orderModel.setUser(user);
            orderModel.setDate(Calendar.getInstance().getTime());
//            List<Integer> counts = new ArrayList<>();
//            counts.add(addToOrderForm.getCount());
//            orderModel.setCounts(counts);
        } else {
            orderModel = orderRepository.findByUserAndStatusOfOrder(user, StatusOfOrder.NOT_PAID).get();
            Set<Product> products = orderModel.getProducts();
//            List<Integer> counts = orderModel.getCounts();
            products.add(productRepository.findById(id_product).get());
//            counts.add(addToOrderForm.getCount());
        }

        orderRepository.save(orderModel);
        return "redirect:/";
    }


    @GetMapping("/profile")
    public ModelAndView userProfile(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("profile");
        UserModel user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();
        modelAndView.addObject("user", user);
        return modelAndView;
    }

}
