package com.example.ra.service.impl;

import com.example.ra.model.entity.InvoiceDetail;
import com.example.ra.repository.InvoiceDetailRepository;
import com.example.ra.service.InvoiceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceDetailImpl implements InvoiceDetailService {
    @Autowired
    InvoiceDetailRepository invoiceDetailRepository;

    @Override
    public InvoiceDetail save(InvoiceDetail invoiceDetail) {
        return invoiceDetailRepository.save(invoiceDetail);
    }

    @Override
    public InvoiceDetail findById(Integer id) {
        return invoiceDetailRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Override
    public List<InvoiceDetail> findByInvoiceId(Integer id) {
        return invoiceDetailRepository.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        invoiceDetailRepository.deleteById(id);
    }
}
