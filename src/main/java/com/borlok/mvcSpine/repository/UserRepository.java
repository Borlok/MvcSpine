package com.borlok.mvcSpine.repository;

import com.borlok.mvcSpine.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Erofeevskiy Yuriy on 29.05.2024
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByPhoneNumber(String phoneNumber);
}
