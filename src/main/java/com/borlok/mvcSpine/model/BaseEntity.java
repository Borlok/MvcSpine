package com.borlok.mvcSpine.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * @author Erofeevskiy Yuriy on 28.05.2024
 */


@Data
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private Date created_at;

    @Column(name = "updated_at", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date updated_at;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}
