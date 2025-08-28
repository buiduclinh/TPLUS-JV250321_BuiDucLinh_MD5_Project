package com.example.ra.service;

import com.example.ra.model.entity.Invoice;
import com.example.ra.model.entity.InvoiceDetail;
import io.micrometer.common.KeyValues;

import java.util.List;

public interface InvoiceDetailService {
    List<InvoiceDetail> findByInvoiceId(Integer id);

    InvoiceDetail save(InvoiceDetail invoiceDetail);

    InvoiceDetail findById(Integer id);

    void deleteById(Integer id);

}
