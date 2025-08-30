package ra.edu.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ra.edu.model.entity.Customer;
import ra.edu.model.entity.Invoice;
import ra.edu.model.entity.InvoiceStatus;
import ra.edu.repository.InvoiceDetailRepository;
import ra.edu.repository.InvoiceRepository;
import ra.edu.service.CustomerService;
import ra.edu.service.InvoiceService;
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
                if (!invoiceDetailRepository.findAllByInvoice_Id(id).isEmpty()) {
                    throw new RuntimeException("Invoice can not be deleted");
                } else {
                    invoiceRepository.deleteById(id);
                }
            }catch (Exception e){
                throw new RuntimeException("Invoice can not be deleted");
            }
        }
    }

    @Override
    public Page<Invoice> search(String keyword, String status, Integer day, Integer month, Integer year, Pageable pageable) {
        InvoiceStatus invoiceStatus = null;
        if (status != null && !status.isEmpty()) {
            invoiceStatus = InvoiceStatus.valueOf(status);
        }
        if (keyword != null && keyword.trim().isEmpty()) keyword = null;

        return invoiceRepository.search(keyword, invoiceStatus, day, month, year, pageable);
    }

    @Override
    public List<Object[]> getRevenueByDay() {
        return invoiceRepository.getRevenueByDay();
    }

    @Override
    public List<Object[]> getRevenueByMonth() {
        return invoiceRepository.getRevenueByMonth();
    }

    @Override
    public List<Object[]> getRevenueByYear() {
        return invoiceRepository.getRevenueByYear();
    }

    @Override
    public List<Integer> getAllInvoiceYears() {
        return invoiceRepository.findAllYears();
    }
}
