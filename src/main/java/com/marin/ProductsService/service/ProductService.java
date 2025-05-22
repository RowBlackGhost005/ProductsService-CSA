package com.marin.ProductsService.service;

import com.marin.ProductsService.entities.Product;

import java.util.List;

public interface ProductService {

    Product saveProduct(Product product);

    Product getProductById(int id);

    List<Product> getProducts();

    Product updateProduct(Product product , int id);

    void deleteProduct(int id);

    boolean unstockProduct(int id , int stock);

    boolean stockProduct(int id , int stock);
}
