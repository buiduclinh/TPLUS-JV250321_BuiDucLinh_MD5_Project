package com.example.ra.controller;

import com.example.ra.model.entity.Admin;
import com.example.ra.service.AdminService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           Model model) {
        try {
            adminService.register(username, password);
            model.addAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
            return "login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        try {
            Admin admin = adminService.login(username, password);
            session.setAttribute("ADMIN", admin.getId());
            return "redirect:/admin/home";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/home")
    public String adminHome(HttpSession session, Model model) {
        if (session.getAttribute("ADMIN") == null) {
            return "redirect:/admin/login";
        }
        model.addAttribute("message", "Chào mừng Admin!");
        return "adminHome";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login?logout=true";
    }
}