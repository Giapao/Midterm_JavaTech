package com.midterm.SpringCommerce.controller;

import com.midterm.SpringCommerce.model.Cart;
import com.midterm.SpringCommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // API: Lấy thông tin giỏ hàng
    @GetMapping
    public Cart getCart() {
        return cartService.getCart();
    }

    // API: Thêm sản phẩm vào giỏ hàng
    @PostMapping
    public void addItemToCart(@RequestParam Long productId,
                              @RequestParam(name = "quantity", defaultValue = "1") int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        cartService.addItemToCart(productId, quantity);
    }

    // API: Xóa sản phẩm khỏi giỏ hàng
    @DeleteMapping("/{productId}")
    public void removeItemFromCart(@PathVariable Long productId) {
        cartService.removeItem(productId);
    }

    // API: Xóa toàn bộ giỏ hàng
    @DeleteMapping
    public void clearCart() {
        cartService.clearCart();
    }
}
