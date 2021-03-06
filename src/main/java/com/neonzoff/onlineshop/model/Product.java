package com.neonzoff.onlineshop.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tseplyaev Dmitry
 */
@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_product")
    private int id;

    @Column(name = "name_product")
    private String name;

    @Column(length = 2048)
    private String description;

    private int count;
    private int price;
    private String photoURL;
    private boolean isAvailable;

    @ManyToMany(mappedBy = "products")
    private List<OrderModel> orders = new ArrayList<>();
}
