package com.neonzoff.onlineshop.dao;

import com.neonzoff.onlineshop.model.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author Tseplyaev Dmitry
 */
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {
    List<Product> findAll();
}
