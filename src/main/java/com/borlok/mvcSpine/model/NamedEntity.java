package com.borlok.mvcSpine.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

/**
 * @author Erofeevskiy Yuriy on 28.05.2024
 */

@MappedSuperclass
@Data
public class NamedEntity extends BaseEntity {
    private String name;
}

