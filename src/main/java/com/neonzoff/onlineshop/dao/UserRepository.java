package com.neonzoff.onlineshop.dao;

import com.neonzoff.onlineshop.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Tseplyaev Dmitry
 */
@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
    Optional<UserModel> findByUsername(String email);
}
