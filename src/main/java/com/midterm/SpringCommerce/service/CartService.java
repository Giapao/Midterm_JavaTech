package com.midterm.SpringCommerce.service;

import com.midterm.SpringCommerce.model.Cart;
import com.midterm.SpringCommerce.repository.CartRepository;
import com.midterm.SpringCommerce.model.CartItem;
import com.midterm.SpringCommerce.model.Product;
import com.midterm.SpringCommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    private Cart cart = new Cart();

    public Cart getCart() {
        return cartRepository.findById(1L).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setItems(new ArrayList<>());
            newCart.setTotalPrice(0);
            return cartRepository.save(newCart); // Lưu giỏ hàng mới vào database
        });
    }

    public void addItemToCart(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + productId + " not found."));

        Cart cart = getCart(); // Lấy giỏ hàng từ database

        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cart.getItems().add(newItem);
        }

        updateTotalPrice(cart); // Tính lại tổng giá
        cartRepository.save(cart); // Lưu giỏ hàng vào cơ sở dữ liệu
    }

    public void removeItem(Long productId) {
        Cart cart = getCart(); // Lấy giỏ hàng từ database

        if (cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is already empty.");
        }

        boolean removed = cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        if (!removed) {
            throw new IllegalArgumentException("Product with ID " + productId + " not found in cart.");
        }

        updateTotalPrice(cart); // Tính lại tổng giá
        cartRepository.save(cart); // Lưu giỏ hàng vào cơ sở dữ liệu
    }


    public void clearCart() {
        cart.getItems().clear(); // Xóa toàn bộ giỏ hàng
        cart.setTotalPrice(0); // Đặt tổng giá về 0
    }

    private void updateTotalPrice(Cart cart) {
        System.out.println("Items in cart before calculating total price: " + cart.getItems());
        double total = cart.getItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
        cart.setTotalPrice(total);
        System.out.println("Total price calculated: " + total);
    }
}