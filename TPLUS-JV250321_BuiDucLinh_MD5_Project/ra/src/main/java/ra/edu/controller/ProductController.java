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

    @GetMapping()
    public String listProducts(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size,
                               @RequestParam(required = false) String keyword,
                               @RequestParam(required = false) String brand,
                               @RequestParam(required = false) Integer minStock,
                               @RequestParam(required = false) Integer maxStock,
                               @RequestParam(required = false) String isDeleted) {
        Boolean deleted = null;
        if ("true".equals(isDeleted)) {
            deleted = true;
        } else if ("false".equals(isDeleted)) {
            deleted = false;
        }
        Page<Product> productPage;

        if ((keyword != null && !keyword.isEmpty())
                || (brand != null && !brand.isEmpty())
                || minStock != null
                || maxStock != null|| deleted != null) {

            productPage = productRepository.search(
                    (keyword != null && !keyword.isEmpty()) ? keyword : null,
                    (brand != null && !brand.isEmpty()) ? brand : null,
                    minStock,
                    maxStock,
                    deleted,
                    PageRequest.of(page, size)
            );
        } else {
            productPage = productRepository.findAll(PageRequest.of(page, size));
        }

        model.addAttribute("productPage", productPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("brand", brand);
        model.addAttribute("minStock", minStock);
        model.addAttribute("maxStock", maxStock);
        model.addAttribute("isDeleted", deleted);

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