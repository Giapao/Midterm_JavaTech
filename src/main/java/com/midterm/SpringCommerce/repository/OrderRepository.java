package com.midterm.SpringCommerce.repository;

import com.midterm.SpringCommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {}