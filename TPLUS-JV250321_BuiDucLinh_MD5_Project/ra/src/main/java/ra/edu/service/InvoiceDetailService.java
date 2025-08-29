package ra.edu.service;

import ra.edu.model.entity.InvoiceDetail;

import java.util.List;

public interface InvoiceDetailService {
    List<InvoiceDetail> findByInvoiceId(Integer id);

    InvoiceDetail save(InvoiceDetail invoiceDetail);

    InvoiceDetail findById(Integer id);

    void deleteById(Integer id);

}
