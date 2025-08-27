package com.example.ra.service;

import com.example.ra.model.entity.Customer;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllActive();
    Customer save(Customer customer);
    Customer findById(Integer id);
    void softDelete(Integer id);
}
