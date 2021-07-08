package com.neonzoff.onlineshop.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Tseplyaev Dmitry
 */
@Getter
@Setter
public class AddToOrderForm {
    private int idProduct;
    private int count;
    private int idUser;
}
