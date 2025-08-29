package ra.edu.controller;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ra.edu.repository.InvoiceRepository;


import java.util.List;

@Controller
@RequestMapping("/revenues")
public class RevenueController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @GetMapping("/days")
    public String revenueByDay(Model model) {
        List<Object[]> revenues = invoiceRepository.getRevenueByDay();
        model.addAttribute("revenues", revenues);
        return "revenueByDay";
    }

    @GetMapping("/months")
    public String revenueByMonth(Model model) {
        List<Object[]> revenues = invoiceRepository.getRevenueByMonth();
        model.addAttribute("revenues", revenues);
        return "revenueByMonth";
    }

    @GetMapping("/years")
    public String revenueByYear(Model model) {
        List<Object[]> revenues = invoiceRepository.getRevenueByYear();
        model.addAttribute("revenues", revenues);
        return "revenueByYear";
    }
}
