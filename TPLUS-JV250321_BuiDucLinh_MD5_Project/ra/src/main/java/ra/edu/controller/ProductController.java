package ra.edu.controller;

import ra.edu.model.entity.Product;
import ra.edu.repository.ProductRepository;
import ra.edu.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductRepository productRepository;

    @GetMapping
    public String listProducts(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size,
                               @RequestParam(required = false) String keyword,
                               @RequestParam(required = false) Integer minStock,
                               @RequestParam(required = false) Integer maxStock) {

        Page<Product> productPage;

        if (keyword != null && !keyword.isEmpty()) {
            productPage = productRepository.findByNameContainingIgnoreCase(keyword, PageRequest.of(page, size));
        } else if (minStock != null && maxStock != null) {
            productPage = productRepository.findByStockBetween(minStock, maxStock, PageRequest.of(page, size));
        } else {
            productPage = productRepository.findAll(PageRequest.of(page, size));
        }

        model.addAttribute("productPage", productPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("minStock", minStock);
        model.addAttribute("maxStock", maxStock);

        return "productList";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("product", new Product());
        return "productForm";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile file) {
        productService.save(product, file);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "productForm";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        productService.softDelete(id);
        return "redirect:/products";
    }

}