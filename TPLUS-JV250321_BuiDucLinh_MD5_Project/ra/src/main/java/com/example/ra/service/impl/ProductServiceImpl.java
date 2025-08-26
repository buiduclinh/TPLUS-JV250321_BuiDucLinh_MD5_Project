package com.example.ra.service.impl;

import com.example.ra.model.entity.Product;
import com.example.ra.repository.ProductRepository;
import com.example.ra.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Override
    public List<Product> getAllActive() {
        return productRepository.findByIsDeletedFalse();
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product findById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public void softDelete(Integer id) {
        Product product = findById(id);
        product.setIsDeleted(true);
        productRepository.save(product);
    }
}
