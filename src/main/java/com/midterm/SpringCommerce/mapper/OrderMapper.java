package com.midterm.SpringCommerce.mapper;

import com.midterm.SpringCommerce.dto.CartItemDTO;
import com.midterm.SpringCommerce.dto.OrderDTO;
import com.midterm.SpringCommerce.model.CartItem;
import com.midterm.SpringCommerce.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderMapper {

    // Chuyển đổi CartItem thành CartItemDTO
    public CartItemDTO toCartItemDTO(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(cartItem.getId());
        cartItemDTO.setProduct(cartItem.getProduct());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        return cartItemDTO;
    }

    // Chuyển đổi Order thành OrderDTO
    public OrderDTO toOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setCustomerName(order.getCustomerName());
        orderDTO.setAddress(order.getAddress());
        orderDTO.setTotalPrice(order.getTotalPrice());

        List<CartItemDTO> itemsDTO = order.getItems().stream()
                .map(this::toCartItemDTO) // Chuyển đổi từng CartItem sang CartItemDTO
                .collect(Collectors.toList());
        orderDTO.setItems(itemsDTO);

        return orderDTO;
    }
}
