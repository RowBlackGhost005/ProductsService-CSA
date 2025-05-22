package com.marin.ProductsService.controller;

import com.marin.ProductsService.entities.Product;
import com.marin.ProductsService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> fetchProducts(){
        List<Product> products = productService.getProducts();

        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> fetchProductById(@PathVariable int id){
        Product product = productService.getProductById(id);

        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> registerProduct(@RequestBody Product product){
        Product registeredProduct = productService.saveProduct(product);

        return ResponseEntity.ok(registeredProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id , @RequestBody Product product){
        Product updatedProduct = productService.updateProduct(product, id);

        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        productService.deleteProduct(id);

        return ResponseEntity.ok("Product deleted!");
    }
}
