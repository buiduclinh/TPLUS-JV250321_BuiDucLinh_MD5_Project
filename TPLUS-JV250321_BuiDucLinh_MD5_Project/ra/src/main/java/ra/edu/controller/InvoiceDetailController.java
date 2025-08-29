package ra.edu.controller;


import ra.edu.model.entity.Invoice;
import ra.edu.model.entity.InvoiceDetail;
import ra.edu.model.entity.Product;
import ra.edu.repository.InvoiceDetailRepository;
import ra.edu.service.InvoiceDetailService;
import ra.edu.service.InvoiceService;
import ra.edu.service.ProductService;
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
        model.addAttribute("invoice", invoice);
        model.addAttribute("invoiceDetail", detail);
        model.addAttribute("products", productList);
        return "invoiceDetailForm";
    }

    @PostMapping("/save/{id}")
    public String saveInvoiceDetail(@PathVariable("id") Integer id,
                                    @ModelAttribute InvoiceDetail invoiceDetail,
                                    Model model) {
        Product product = productService.findById(invoiceDetail.getProduct().getId());

        if (invoiceDetail.getQuantity() > product.getStock()) {
            Invoice invoice = invoiceService.findById(id);
            List<Product> productList = productService.getAllActive();

            model.addAttribute("invoice", invoice);
            model.addAttribute("invoiceDetail", invoiceDetail);
            model.addAttribute("products", productList);
            model.addAttribute("errorMessage", "Số lượng yêu cầu vượt quá tồn kho! (Còn lại: " + product.getStock() + ")");

            return "invoiceDetailForm";
        }

        invoiceDetail.setProduct(product);
        invoiceDetail.setUnitPrice(product.getPrice());

        product.setStock(product.getStock() - invoiceDetail.getQuantity());
        productService.save(product);

        invoiceDetailService.save(invoiceDetail);

        Invoice invoice = invoiceService.findById(id);
        BigDecimal total = invoice.getTotalAmount()
                .add(invoiceDetail.getUnitPrice().multiply(BigDecimal.valueOf(invoiceDetail.getQuantity())));

        invoice.setTotalAmount(total);
        invoiceService.save(invoice);

        return "redirect:/invoiceDetails/" + invoice.getId();
    }


    @GetMapping("/delete")
    public String deleteInvoiceDetail(@RequestParam("id") int id) {
        invoiceDetailService.deleteById(id);
        return "redirect:/invoiceDetails";
    }

}