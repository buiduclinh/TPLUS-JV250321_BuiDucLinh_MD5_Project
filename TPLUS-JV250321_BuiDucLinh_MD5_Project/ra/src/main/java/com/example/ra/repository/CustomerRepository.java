package com.example.ra.repository;

import com.example.ra.model.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    List<Customer> findByIsDeletedFalse();

    Page<Customer> findByNameContainingIgnoreCase(String keyword, PageRequest of);
}
