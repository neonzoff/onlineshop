package com.neonzoff.onlineshop.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Tseplyaev Dmitry
 */
@Getter
@Setter
public class UserForm {
    private String surname;
    private String password;
    private String name;
    private String username;
    private String middlename;
    private String address;
    private String phoneNumber;
}
