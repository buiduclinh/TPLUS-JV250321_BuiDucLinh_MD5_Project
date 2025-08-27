package com.example.ra.service.impl;

import com.example.ra.model.entity.Product;
import com.example.ra.repository.ProductRepository;
import com.example.ra.service.CloudinaryService;
import com.example.ra.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    public List<Product> getAllActive() {
        return productRepository.findByIsDeletedFalse();
    }

    @Override
    public Product save(Product product, MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            String imageUrl = cloudinaryService.uploadImage(file);
            product.setImage(imageUrl);
        }
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
