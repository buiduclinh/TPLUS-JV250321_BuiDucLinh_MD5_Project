package ra.edu.controller;

import org.springframework.data.domain.PageImpl;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ra.edu.model.entity.Customer;
import ra.edu.model.entity.Invoice;
import ra.edu.model.entity.InvoiceDetail;
import ra.edu.model.entity.InvoiceStatus;
import ra.edu.repository.CustomerRepository;
import ra.edu.repository.InvoiceRepository;
import ra.edu.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;
    private final InvoiceRepository invoiceRepository;

    private final CustomerRepository customerRepository;

    @GetMapping
    public String listInvoices(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "status", required = false) String status,
                               @RequestParam(value = "day", required = false) Integer day,
                               @RequestParam(value = "month", required = false) Integer month,
                               @RequestParam(value = "year", required = false) Integer year) {
        Page<Invoice> invoicePage;

        if ((keyword != null && !keyword.isEmpty()) || (status != null && !status.isEmpty())
                || day != null || month != null || year != null) {
           invoicePage = invoiceService.search(keyword, status, day, month, year, PageRequest.of(page, size));
        } else {
            invoicePage = invoiceRepository.findAll(PageRequest.of(page, size));
        }

        model.addAttribute("invoicePage", invoicePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", invoicePage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("status", status);
        model.addAttribute("day", day);
        model.addAttribute("month", month);
        model.addAttribute("year", year);
        model.addAttribute("years", invoiceService.getAllInvoiceYears());
        model.addAttribute("statuses", InvoiceStatus.values());

        return "invoiceList";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("invoice", new Invoice());
        List<Customer> activeCustomers = customerRepository.findByIsDeletedFalse();
        model.addAttribute("customers", activeCustomers);
        return "invoiceForm";
    }

    @PostMapping("/save")
    public String saveInvoice(@ModelAttribute Invoice invoice, Model model) {
        Customer customer = invoice.getCustomer();
        if (customer == null || Boolean.TRUE.equals(customer.getIsDeleted())) {
            model.addAttribute("errors", "Khách hàng không hợp lệ hoặc đã bị xóa!");
            return "invoiceForm";
        }

        if (invoice.getId() == null) {
            invoice.setTotalAmount(BigDecimal.ZERO);
            invoiceRepository.save(invoice);
        } else {
            Invoice existingInvoice = invoiceService.findById(invoice.getId());

            if (existingInvoice == null) {
                model.addAttribute("errors", "Không tìm thấy hóa đơn!");
                return "invoiceForm";
            }

            invoice.setInvoiceDetails(existingInvoice.getInvoiceDetails());

            existingInvoice.setStatus(invoice.getStatus());
            existingInvoice.setCustomer(invoice.getCustomer());
            existingInvoice.setTotalAmount(invoice.getTotalAmount());

            existingInvoice.setInvoiceDetails(invoice.getInvoiceDetails());

            invoiceService.save(existingInvoice);
        }

        return "redirect:/invoices";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("invoice", invoiceService.findById(id));
        return "invoiceForm";
    }

    @GetMapping("/delete/{id}")
    public String deleteInvoice(@PathVariable("id") Integer id, RedirectAttributes redirectAttrs) {
        try {
            invoiceService.delete(id);
            redirectAttrs.addFlashAttribute("success", "Xóa hóa đơn thành công");
        } catch (RuntimeException e) {
            redirectAttrs.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/invoices";
    }
}