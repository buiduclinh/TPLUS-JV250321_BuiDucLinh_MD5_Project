package com.example.ra.service.impl;

import com.example.ra.model.entity.Admin;
import com.example.ra.repository.AdminRepository;
import com.example.ra.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;
//    private PasswordEncoder passwordEncoder;

    @Override
    public Admin register(String username, String rawPassword) {
        if (adminRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username đã tồn tại!");
        }

        if (!isValidPassword(rawPassword)) {
            throw new RuntimeException("Password phải >=6 ký tự, gồm chữ hoa, chữ thường, số và ký tự đặc biệt");
        }

        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(rawPassword);
//        admin.setPassword(passwordEncoder.encode(rawPassword));
        return adminRepository.save(admin);
    }

    @Override
    public Admin login(String username, String rawPassword) {
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return admin;
    }

    @Override
    public boolean isValidPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#]).{6,}$";
        return password.matches(regex);
    }

}
