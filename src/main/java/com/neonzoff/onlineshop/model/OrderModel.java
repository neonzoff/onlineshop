package com.neonzoff.onlineshop.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

/**
 * @author Tseplyaev Dmitry
 */
@Entity
@Getter
@Setter
@Table(name = "orders")
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_order")
    private int id;

    private Date date;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private UserModel user;

    @Enumerated(value = EnumType.STRING)
    @JoinColumn(name = "status_of_order")
    private StatusOfOrder statusOfOrder;

/*
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_order")
    private List<Product> products;
*/

//    @ManyToMany(mappedBy = "orders")
//    private List<Product> products;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id")
    )
    private Set<Product> products = new HashSet<>();

    public void addProduct(Product product) {
        this.products.add(product);
        product.getOrders().add(this);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
        product.getOrders().remove(this);
    }
}
