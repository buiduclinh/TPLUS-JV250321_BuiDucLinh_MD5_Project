package com.example.ra.service.impl;


import com.example.ra.model.entity.Customer;
import com.example.ra.repository.CustomerRepository;
import com.example.ra.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllActive() {
        return customerRepository.findByIsDeletedFalse();
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer findById(Integer id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Override
    public void softDelete(Integer id) {
        Customer customer = findById(id);
        customer.setIsDeleted(true);
        customerRepository.save(customer);
    }
}
