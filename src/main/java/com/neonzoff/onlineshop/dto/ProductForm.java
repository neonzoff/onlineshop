package com.neonzoff.onlineshop.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

/**
 * @author Tseplyaev Dmitry
 */
@Getter
@Setter
public class ProductForm {
    @Size(min = 1, max = 50, message = "Длина названия товара должна быть от 1 до 50 символов.")
    private String name;

    @Min(value = 1, message = "Количество товара должно быть больше 1.")
    @Max(value = Integer.MAX_VALUE - 1, message = "Количество товара должно быть меньше " + Integer.MAX_VALUE)
    private int count;

    @Min(value = 1, message = "Стоимость товара должна быть больше 1.")
    @Max(value = 10000, message = "Стоимость товара должна быть меньше 10000, у нас экономный магазин.")
    private int price;

    @NotEmpty
    private String photoURL;

    @Size(min = 1, max = 2048, message = "Длина описания товара должна быть от 1 до 2048 символов.")
    private String description;

    private boolean isAvailable;
}
