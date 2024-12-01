package com.midterm.SpringCommerce.service;

import com.midterm.SpringCommerce.model.Product;
import com.midterm.SpringCommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getProducts(String category, double minPrice, double maxPrice) {
        return productRepository.findByCategoryAndPriceBetween(category, minPrice, maxPrice);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + id));
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));

        // Xóa sản phẩm, các CartItem liên quan sẽ tự động bị xóa
        productRepository.delete(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll(); // Lấy tất cả sản phẩm
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        product.setBrand(productDetails.getBrand());
        product.setCategory(productDetails.getCategory());
        product.setColor(productDetails.getColor());
        return productRepository.save(product);
    }
}