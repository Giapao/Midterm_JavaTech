package com.midterm.SpringCommerce.controller;

import com.midterm.SpringCommerce.model.Product;
import com.midterm.SpringCommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // API: Lấy danh sách sản phẩm (GET)
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // API: Thêm sản phẩm mới (POST)
    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    // API: Xóa sản phẩm (DELETE)
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    // API: Cập nhật sản phẩm (PUT)
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        return productService.updateProduct(id, productDetails);
    }

    // API: Lọc sản phẩm (GET với tham số)
    @GetMapping("/filter")
    public List<Product> getFilteredProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false, defaultValue = "0") double minPrice,
            @RequestParam(required = false, defaultValue = "1000000") double maxPrice
    ) {
        return productService.getProducts(category, minPrice, maxPrice);
    }
}
