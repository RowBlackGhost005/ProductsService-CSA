package com.marin.ProductsService.service;

import com.marin.ProductsService.entities.Product;
import com.marin.ProductsService.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImp(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Product> getProducts() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Product updateProduct(Product product, int id) {
        Product productDB = productRepository.findById(id).orElseThrow();

        if(product.getName() != null && !product.getName().isBlank()){
            productDB.setName(product.getName());
        }

        if(product.getDescription() != null && !product.getDescription().isBlank()){
            productDB.setDescription(product.getDescription());
        }

        if(product.getPrice() > 0){
            productDB.setPrice(product.getPrice());
        }

        if(product.getStock() > 0){
            productDB.setStock(product.getStock());
        }

        return productRepository.save(productDB);
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public boolean unstockProduct(int id, int stock) {

        if(stock <= 0){
            //throw new InvalidParameterException("Invalid stock quantity to remove");
        }

        Product productDB = productRepository.findById(id).orElseThrow();

        if(stock > productDB.getStock()){
            //throw new InsufficientStockException("Insufficient stock");
        }

        productDB.setStock(productDB.getStock() - stock);

        productRepository.save(productDB);

        return true;
    }

    @Override
    public boolean stockProduct(int id, int stock) {

        if(stock <= 0){
            //throw new InvalidParameterException("Invalid stock quantity to remove");
        }

        Product productDB = productRepository.findById(id).orElseThrow();

        productDB.setStock(productDB.getStock() + stock);

        productRepository.save(productDB);

        return true;
    }
}
