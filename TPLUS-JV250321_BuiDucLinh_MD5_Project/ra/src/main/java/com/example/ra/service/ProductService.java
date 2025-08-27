package com.example.ra.service;

import com.example.ra.model.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<Product> getAllActive();
    Product save(Product product, MultipartFile file);
    Product findById(Integer id);
    void softDelete(Integer id);
}
