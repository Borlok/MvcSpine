package com.borlok.mvcSpine.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Erofeevskiy Yuriy on 29.05.2024
 */

@Data
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {
    @Transient
    private List<User> users;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birthday")
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthday;
}
