package com.neonzoff.onlineshop.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author Tseplyaev Dmitry
 */
@Getter
@Setter
public class UserForm {
    @Size(min = 7, max = 40, message = "Длинна фамилии должна быть от 7 до 40.")
    private String surname;
    @Size(min = 4, max = 40, message = "Длинна пароля должна быть от 4 до 40.")
    private String password;
    @Size(min = 4, max = 40, message = "Длинна имени должна быть от 4 до 40.")
    private String name;
    @Email
    private String username;
    @Size(min = 4, max = 40, message = "Длинна отчества должна быть от 4 до 40.")
    private String middlename;
    @Size(min = 2, max = 80, message = "Длинна адреса должна быть от 2 до 80.")
    private String address;
    @Pattern(regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$", message = "Неверный формат номера.")
    private String phoneNumber;
}
