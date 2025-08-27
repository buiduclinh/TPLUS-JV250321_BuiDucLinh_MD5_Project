package com.example.ra.controller;

import com.example.ra.model.entity.Customer;
import com.example.ra.model.entity.Product;
import com.example.ra.repository.CustomerRepository;
import com.example.ra.repository.ProductRepository;
import com.example.ra.service.CustomerService;
import com.example.ra.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;

    @GetMapping
    public String listCustomers(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size,
                               @RequestParam(required = false) String keyword) {

        Page<Customer> customerPage;

        if (keyword != null && !keyword.isEmpty()) {
            customerPage = customerRepository.findByNameContainingIgnoreCase(keyword, PageRequest.of(page, size));
        } else {
            customerPage = customerRepository.findAll(PageRequest.of(page, size));
        }

        model.addAttribute("customerPage", customerPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", customerPage.getTotalPages());
        model.addAttribute("keyword", keyword);

        return "customerList";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customerForm";
    }

    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute Customer customer) {
        customerService.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        return "customerForm";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        customerService.softDelete(id);
        return "redirect:/customers";
    }

}