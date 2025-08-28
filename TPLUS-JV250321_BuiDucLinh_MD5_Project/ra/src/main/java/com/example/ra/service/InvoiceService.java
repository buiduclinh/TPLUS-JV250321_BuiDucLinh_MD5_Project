package com.example.ra.service;


import com.example.ra.model.entity.Invoice;

import java.util.List;

public interface InvoiceService {
    List<Invoice> getAllActive();
    Invoice save(Invoice invoice);
    Invoice findById(Integer id);
    void delete(Integer id);
}
