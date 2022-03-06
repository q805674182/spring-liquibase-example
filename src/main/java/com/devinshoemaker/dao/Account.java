package com.devinshoemaker.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Data access object for the account table.
 *
 * @author Devin Shoemaker (devinshoe@gmail.com)
 */
@Getter
@Setter
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private boolean active;


}
