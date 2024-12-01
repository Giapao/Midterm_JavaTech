package com.midterm.SpringCommerce.controller;

import com.midterm.SpringCommerce.dto.OrderDTO;
import com.midterm.SpringCommerce.model.Cart;
import com.midterm.SpringCommerce.model.Order;
import com.midterm.SpringCommerce.service.CartService;
import com.midterm.SpringCommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    // API: Tạo đơn hàng từ giỏ hàng
    @PostMapping
    public Order createOrder(@RequestParam String customerName, @RequestParam String address) {
        Cart cart = cartService.getCart();
        if (cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty. Cannot create order.");
        }
        return orderService.createOrder(cart, customerName, address);
    }

    // API: Lấy chi tiết đơn hàng theo ID
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long orderId) {
        OrderDTO orderDTO = orderService.getOrderById(orderId); // Trả về DTO
        return ResponseEntity.ok(orderDTO);
    }

    // API: Lấy danh sách tất cả đơn hàng
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders(); // Trả về danh sách DTO
        return ResponseEntity.ok(orders);
    }
}