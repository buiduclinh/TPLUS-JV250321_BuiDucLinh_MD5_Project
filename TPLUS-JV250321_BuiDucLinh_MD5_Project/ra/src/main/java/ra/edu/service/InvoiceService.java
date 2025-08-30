package ra.edu.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.edu.model.entity.Invoice;

import java.util.List;

public interface InvoiceService {
    List<Invoice> getAllActive();
    Invoice save(Invoice invoice);
    Invoice findById(Integer id);
    void delete(Integer id);
    Page<Invoice> search(String keyword, String status, Integer day, Integer month, Integer year, Pageable pageable);
    List<Object[]> getRevenueByDay();
    List<Object[]> getRevenueByMonth();
    List<Object[]> getRevenueByYear();
    List<Integer> getAllInvoiceYears();
}
