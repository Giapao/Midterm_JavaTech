package com.midterm.SpringCommerce.repository;

import com.midterm.SpringCommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryAndPriceBetween(String category, double minPrice, double maxPrice);
    List<Product> findByBrand(String brand);
    List<Product> findByColor(String color);
}
