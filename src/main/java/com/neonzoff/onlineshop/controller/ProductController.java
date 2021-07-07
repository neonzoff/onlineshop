package com.neonzoff.onlineshop.controller;

import com.neonzoff.onlineshop.dao.ProductRepository;
import com.neonzoff.onlineshop.dto.ProductForm;
import com.neonzoff.onlineshop.model.PagerModel;
import com.neonzoff.onlineshop.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

/**
 * @author Tseplyaev Dmitry
 */
@Controller
@PreAuthorize(value = "hasAuthority('ADMIN')")
@RequestMapping("/product")
public class ProductController {
    private final ProductRepository repository;
    private static final int COUNT_OF_RECORDS = 3;
    private static final int BUTTONS_TO_SHOW = 5;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 5;
    private static final int[] PAGE_SIZES = {5, 10, 15, 30, 50};

    @Autowired
    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping()
    public ModelAndView products(@RequestParam("pageSize") Optional<Integer> pageSize,
                                 @RequestParam("page") Optional<Integer> page) {


        ModelAndView modelAndView = new ModelAndView("all_products");
        //
        // Evaluate page size. If requested parameter is null, return initial
        // page size
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        // print repo
//        System.out.println("here is client repo " + repository.findAll());
        Page<Product> productList = repository.findAll(PageRequest.of(evalPage, evalPageSize));
//        System.out.println("client list get total pages" + userList.getTotalPages() + "client list get number " + userList.getNumber());
        PagerModel pager = new PagerModel(productList.getTotalPages(), productList.getNumber(), BUTTONS_TO_SHOW);
        // add clientmodel
        modelAndView.addObject("productList", productList);
        // evaluate page size
        modelAndView.addObject("selectedPageSize", evalPageSize);
        // add page sizes
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        // add pager
        modelAndView.addObject("pager", pager);
        return modelAndView;

    }


    @GetMapping("/add")
    public String addProduct(Model model) {
        model.addAttribute("productForm", new ProductForm());
        return "add_product";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute("productForm") ProductForm productForm) {
        Product product = new Product();
        product.setCount(productForm.getCount());
        product.setName(productForm.getName());
        product.setDescription(productForm.getDescription());
        product.setPhotoURL(productForm.getPhotoURL());
        product.setPrice(productForm.getPrice());
        product.setAvailable(productForm.isAvailable());
        repository.save(product);
        return "redirect:/product";
    }

    @GetMapping("/edit/{id_product}")
    public ModelAndView editProduct(@PathVariable int id_product) {
        ModelAndView modelAndView = new ModelAndView("edit_product");
        Product product = repository.findById(id_product).get();
        modelAndView.addObject("product", product);
        return modelAndView;
    }


}
