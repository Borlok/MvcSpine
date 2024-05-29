package com.borlok.mvcSpine.model;

import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Erofeevskiy Yuriy on 28.05.2024
 */

@Entity
@Table(name = "users")
@Data
public class User extends BaseEntity {
    @Column(name = "phone_number", unique = true)
    @NotNull
    private String phoneNumber;
    @Column(name = "password", length = 100)
    @NotNull
    private String password;

    @Column(name = "first_name")
    @NotNull
    private String firstName;

    @Column(name = "last_name")
    @NotNull
    private String lastName;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "last_password_reset_date")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date lastPasswordResetDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status")
    private UserStatus userStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType;
    @Column(name = "avatar_url")
    private String avatarUrl;
}
