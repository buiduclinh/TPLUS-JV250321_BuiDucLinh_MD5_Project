package ra.edu.controller;

import org.springframework.data.domain.PageImpl;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ra.edu.model.entity.Invoice;
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

    @GetMapping
    public String listInvoices(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size,
                               @RequestParam(value = "keyword", required = false) String keyword
    ) {
        Page<Invoice> invoicePage;

        if (keyword != null && !keyword.isEmpty()) {
            // Nếu có search thì lấy danh sách rồi wrap vào Page
            List<Invoice> invoices = invoiceService.search(keyword);
            invoicePage = new PageImpl<>(invoices, PageRequest.of(page, size), invoices.size());
        } else {
            invoicePage = invoiceRepository.findAll(PageRequest.of(page, size));
        }

        model.addAttribute("invoicePage", invoicePage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", invoicePage.getTotalPages());
        model.addAttribute("keyword", keyword);

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