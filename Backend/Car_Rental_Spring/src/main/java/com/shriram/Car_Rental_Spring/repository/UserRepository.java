package com.shriram.Car_Rental_Spring.repository;

import com.shriram.Car_Rental_Spring.entity.User;
import com.shriram.Car_Rental_Spring.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findFirstByEmail(String email);
    User findByUserRole(UserRole admin);
}
