package ra.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ra.edu.repository.InvoiceRepository;

import java.util.List;

@Controller
public class HomeController {

    private final InvoiceRepository invoiceRepository;

    public HomeController(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @GetMapping("/home")
    public String home(Model model) {
        List<Object[]> revenuesByDay = invoiceRepository.getRevenueByDay();
        model.addAttribute("revenuesByDay", revenuesByDay);

        List<Object[]> revenuesByMonth = invoiceRepository.getRevenueByMonth();
        model.addAttribute("revenuesByMonth", revenuesByMonth);

        List<Object[]> revenuesByYear = invoiceRepository.getRevenueByYear();
        model.addAttribute("revenuesByYear", revenuesByYear);

        return "home";
    }
}