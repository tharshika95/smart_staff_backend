package com.smart.staff.auth.service;


import com.smart.staff.auth.entity.User;

public interface UserService {
    User getUserById(Long userId);

    User getUserByEmail(String email);
}
