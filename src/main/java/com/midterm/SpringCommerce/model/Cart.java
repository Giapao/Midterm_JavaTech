package com.midterm.SpringCommerce.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "cart_id") // Liên kết các CartItem với Cart
    private List<CartItem> items = new ArrayList<>();

    private double totalPrice;

    // Hàm thêm item vào giỏ hàng
    public void addItem(CartItem item) {
        this.items.add(item);
        recalculateTotalPrice();
    }

    // Hàm xóa item khỏi giỏ hàng
    public void removeItem(CartItem item) {
        this.items.remove(item);
        recalculateTotalPrice();
    }

    // Hàm xóa tất cả các item khỏi giỏ hàng
    public void clearItems() {
        this.items.clear();
        this.totalPrice = 0.0; // Đặt lại giá trị totalPrice
    }

    // Hàm tính lại tổng giá trị của giỏ hàng
    private void recalculateTotalPrice() {
        this.totalPrice = this.items.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }
}

