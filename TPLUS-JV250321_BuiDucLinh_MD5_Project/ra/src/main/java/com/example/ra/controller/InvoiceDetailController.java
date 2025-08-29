package com.example.ra.controller;


import com.example.ra.model.entity.Invoice;
import com.example.ra.model.entity.InvoiceDetail;
import com.example.ra.model.entity.Product;
import com.example.ra.repository.InvoiceDetailRepository;
import com.example.ra.repository.InvoiceRepository;
import com.example.ra.service.InvoiceDetailService;
import com.example.ra.service.InvoiceService;
import com.example.ra.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/invoiceDetails")
public class InvoiceDetailController {
    private final InvoiceDetailService invoiceDetailService;
    private final InvoiceDetailRepository invoiceDetailRepository;
    private final InvoiceService invoiceService;
    private final ProductService productService;

    @GetMapping("/{id}")
    public String listInvoiceDetails(@PathVariable("id") int id,Model model) {
        List<InvoiceDetail> invoiceDetails;
            invoiceDetails = invoiceDetailService.findByInvoiceId(id);
            Invoice invoice = invoiceService.findById(id);
        model.addAttribute("invoice",invoice);
        model.addAttribute("invoiceDetails", invoiceDetails);
        return "invoiceDetailList";
    }

    @GetMapping("/create/{id}")
    public String addInvoiceDetail(@PathVariable("id") int id, Model model) {
        Invoice invoice = invoiceService.findById(id);

        InvoiceDetail detail = new InvoiceDetail();
        detail.setInvoice(invoice);
        List<Product> productList = productService.getAllActive();
        model.addAttribute("invoiceDetail", detail);
        model.addAttribute("products", productList);
        return "invoiceDetailForm";
    }

    @PostMapping("/save")
    public String saveInvoiceDetail(@ModelAttribute InvoiceDetail invoiceDetail) {
        Product product = productService.findById(invoiceDetail.getProduct().getId());
        invoiceDetail.setProduct(product);
        invoiceDetail.setUnitPrice(product.getPrice());
        invoiceDetailService.save(invoiceDetail);
        Invoice invoice = invoiceService.findById(invoiceDetail.getInvoice().getId());
        BigDecimal total = invoiceDetail.getUnitPrice()
                .multiply(BigDecimal.valueOf(invoiceDetail.getQuantity()));

        invoiceDetail.getInvoice().setTotalAmount(total);

        invoice.setTotalAmount(total);
        invoiceService.save(invoice);

        return "redirect:/invoiceDetails";
    }


    @GetMapping("/delete")
    public String deleteInvoiceDetail(@RequestParam("id") int id) {
        invoiceDetailService.deleteById(id);
        return "redirect:/invoiceDetails";
    }

}