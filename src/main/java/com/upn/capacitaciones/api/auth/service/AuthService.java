package com.upn.capacitaciones.api.auth.service;

import com.upn.capacitaciones.api.auth.dto.LoginRequest;
import com.upn.capacitaciones.api.auth.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}