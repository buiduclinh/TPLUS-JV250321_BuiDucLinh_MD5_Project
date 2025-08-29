package ra.edu.service.impl;

import ra.edu.model.entity.InvoiceDetail;
import ra.edu.repository.InvoiceDetailRepository;
import ra.edu.service.InvoiceDetailService;
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
        return invoiceDetailRepository.findAllByInvoice_Id(id);
    }

    @Override
    public void deleteById(Integer id) {
        invoiceDetailRepository.deleteById(id);
    }
}
