package com.midterm.SpringCommerce.service;

import com.midterm.SpringCommerce.dto.OrderDTO;
import com.midterm.SpringCommerce.mapper.OrderMapper;
import com.midterm.SpringCommerce.model.Cart;
import com.midterm.SpringCommerce.model.CartItem;
import com.midterm.SpringCommerce.model.Order;
import com.midterm.SpringCommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderRepository orderRepository;

    // Tạo đơn hàng từ giỏ hàng
    @Transactional
    public Order createOrder(Cart cart, String customerName, String address) {
        // Kiểm tra giỏ hàng rỗng
        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty. Cannot create order.");
        }

        // Tạo bản sao của danh sách items
        List<CartItem> itemsCopy = new ArrayList<>();
        for (CartItem item : cart.getItems()) {
            CartItem newItem = new CartItem();
            newItem.setProduct(item.getProduct());
            newItem.setQuantity(item.getQuantity());
            itemsCopy.add(newItem);
        }

        // Tạo đối tượng Order
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setAddress(address);
        order.setItems(itemsCopy); // Sử dụng bản sao của items
        order.setTotalPrice(cart.getTotalPrice()); // Cập nhật tổng giá trị đơn hàng

        try {
            // Lưu Order vào database
            orderRepository.save(order);
            // Làm trống giỏ hàng
            cartService.clearCart();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create order: " + e.getMessage(), e);
        }

        return order;
    }
@Autowired
OrderMapper orderMapper;
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::toOrderDTO) // Chuyển từng Order sang OrderDTO
                .collect(Collectors.toList());
    }

    // Lấy chi tiết đơn hàng theo ID
    public OrderDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));
        return orderMapper.toOrderDTO(order); // Chuyển sang DTO
    }
}