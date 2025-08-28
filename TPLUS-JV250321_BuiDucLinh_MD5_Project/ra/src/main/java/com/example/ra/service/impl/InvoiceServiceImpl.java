package com.example.ra.service.impl;

import com.example.ra.model.entity.Customer;
import com.example.ra.model.entity.Invoice;
import com.example.ra.repository.InvoiceDetailRepository;
import com.example.ra.repository.InvoiceRepository;
import com.example.ra.service.CustomerService;
import com.example.ra.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private CustomerService customerService;
    @Override
    public List<Invoice> getAllActive() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice save(Invoice invoice) {
        int customerId = invoice.getCustomer().getId();
        Customer customer = customerService.findById(customerId);
        if(customer == null){
            throw new RuntimeException("Customer not found");
        }
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice findById(Integer id) {
        return invoiceRepository.findById(id).orElseThrow(() -> new RuntimeException("Invoice not found"));
    }

    @Override
    public void delete(Integer id) {
        if (invoiceRepository.findById(id).isPresent()) {
            try {
                if (invoiceDetailRepository.findById(id).isPresent()) {
                    throw new RuntimeException("Invoice can not be deleted");
                } else {
                    invoiceRepository.deleteById(id);
                }
            }catch (Exception e){
                throw new RuntimeException("Invoice can not be deleted");
            }
        }
    }
}
