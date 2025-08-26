package com.example.ra.service;

import com.example.ra.model.entity.Admin;

public interface AdminService {
    Admin register(String username, String rawPassword);
    Admin login(String username, String rawPassword);
    boolean isValidPassword(String password);
}
