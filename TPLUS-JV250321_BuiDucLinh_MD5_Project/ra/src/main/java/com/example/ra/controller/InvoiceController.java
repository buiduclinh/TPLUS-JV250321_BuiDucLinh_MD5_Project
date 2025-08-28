package com.example.ra.controller;

import com.example.ra.model.entity.Customer;
import com.example.ra.model.entity.Invoice;
import com.example.ra.repository.CustomerRepository;
import com.example.ra.repository.InvoiceRepository;
import com.example.ra.service.CustomerService;
import com.example.ra.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@Controller
@RequiredArgsConstructor
@RequestMapping("/invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;
    private final InvoiceRepository invoiceRepository;

    @GetMapping
    public String listInvoices(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size
    ) {

        Page<Invoice> invoicePage;

        invoicePage = invoiceRepository.findAll(PageRequest.of(page, size));

        model.addAttribute("invoicePage", invoicePage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", invoicePage.getTotalPages());

        return "invoiceList";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("invoice", new Invoice());
        return "invoiceForm";
    }

    @PostMapping("/save")
    public String saveInvoice(@ModelAttribute Invoice invoice) {
        if (invoice.getId() == null) {
            invoice.setTotalAmount(BigDecimal.ZERO);
            invoiceRepository.save(invoice);
            return "redirect:/invoices";
        }
        invoiceService.save(invoice);
        return "redirect:/invoices";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("invoice", invoiceService.findById(id));
        return "invoiceForm";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        invoiceService.delete(id);
        return "redirect:/invoices";
    }

}