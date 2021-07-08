package com.neonzoff.onlineshop.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Tseplyaev Dmitry
 */
@Entity
@Getter
@Setter
public class AccountRefill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_refill")
    private int id;

    private Date date;
    private int money;

    @ManyToOne
    @JoinColumn(name = "id_private_account")
    private PrivateAccount privateAccount;
}
