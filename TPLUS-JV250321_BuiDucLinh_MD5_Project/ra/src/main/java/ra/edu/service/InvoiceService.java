package ra.edu.service;


import ra.edu.model.entity.Invoice;

import java.util.List;

public interface InvoiceService {
    List<Invoice> getAllActive();
    Invoice save(Invoice invoice);
    Invoice findById(Integer id);
    void delete(Integer id);
    List<Invoice> search(String keyword);
    List<Object[]> getRevenueByDay();
    List<Object[]> getRevenueByMonth();
    List<Object[]> getRevenueByYear();
}
