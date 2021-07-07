package com.neonzoff.onlineshop.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Tseplyaev Dmitry
 */
@Getter
@Setter
public class ProductForm {
    private String name;
    private int count;
    private int price;
    private String photoURL;
    private String description;
    private boolean isAvailable;
}
