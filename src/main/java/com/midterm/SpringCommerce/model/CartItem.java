package com.midterm.SpringCommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    private int quantity;

    @ManyToOne
    @JsonIgnore
    private Order order; // Thêm tham chiếu ngược nếu cần

    @Override
    public String toString() {
        return "CartItem{id=" + id + ", productId=" + (product != null ? product.getId() : "null") + ", quantity=" + quantity + "}";
    }

}