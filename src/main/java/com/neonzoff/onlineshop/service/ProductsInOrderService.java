package com.neonzoff.onlineshop.service;

import com.neonzoff.onlineshop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Tseplyaev Dmitry
 */
@Service
public class ProductsInOrderService {
    private List<Product> products;

    public Page<Product> findPaginated(Pageable pageable, List<Product> orderList) {
        products = new ArrayList<>(orderList);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Product> list;

        if (products.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, products.size());
            list = products.subList(startItem, toIndex);
        }

        Page<Product> productPage = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), products.size());

        return productPage;
    }
}
