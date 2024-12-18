package com.midterm.SpringCommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.ArrayList;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private String brand;
    private String category;
    private String color;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Ngăn vòng lặp JSON
    private List<CartItem> cartItems = new ArrayList<>();

    @Override
    public String toString() {
        return "Product{id=" + id + ", name='" + name + "', price=" + price + ", brand='" + brand + "', category='" + category + "', color='" + color + "'}";
    }
}
