package ra.edu.controller;

import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import ra.edu.model.entity.Customer;
import ra.edu.repository.CustomerRepository;
import ra.edu.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


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
                                @RequestParam(required = false) String keyword,
                                @RequestParam(required = false) String isDeleted
                                ) {


        Boolean deletedFilter = null;
        if (isDeleted != null && !isDeleted.trim().isEmpty()) {
            deletedFilter = Boolean.valueOf(isDeleted);
        }

        Page<Customer> customerPage = customerService.search(keyword,deletedFilter, page, size);

        model.addAttribute("customerPage", customerPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", customerPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("isDeleted", deletedFilter);

        return "customerList";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customerForm";
    }

    @PostMapping("/save")
    public String saveCustomer(@Valid @ModelAttribute Customer customer,
                               BindingResult bindingResult) {

        if (customer.getId() == null) {
            if (customerRepository.existsByEmail(customer.getEmail())) {
                bindingResult.rejectValue("email", "error.customer", "Email đã tồn tại!");
            }
            if (customerRepository.existsByPhone(customer.getPhone())) {
                bindingResult.rejectValue("phone", "error.customer", "Số điện thoại đã tồn tại!");
            }
        }

        if (bindingResult.hasErrors()) {
            return "customerForm";
        }

        customerRepository.save(customer);
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