package com.neonzoff.onlineshop.dao;

import com.neonzoff.onlineshop.model.OrderModel;
import com.neonzoff.onlineshop.model.StatusOfOrder;
import com.neonzoff.onlineshop.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Tseplyaev Dmitry
 */
public interface OrderRepository extends JpaRepository<OrderModel, Integer> {
    Optional<OrderModel> findByUserAndStatusOfOrder(UserModel user, StatusOfOrder statusOfOrder);

    Optional<List<OrderModel>> findByUser(UserModel user);
}
