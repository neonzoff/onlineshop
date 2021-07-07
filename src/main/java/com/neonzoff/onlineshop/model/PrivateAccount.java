package com.neonzoff.onlineshop.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Tseplyaev Dmitry
 */
@Entity
@Getter
@Setter
public class PrivateAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_private_account")
    private int id;

    private int balance;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private UserModel user;
}
