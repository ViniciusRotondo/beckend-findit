package com.project.findit.controllers;

import com.project.findit.dtos.UserLoginDto;
import com.project.findit.models.UserModel;
import com.project.findit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto loginDto) {
        // 1) busca usuário pelo e-mail
        Optional<UserModel> optionalUser = userService.findByEmail(loginDto.email());

        if (optionalUser.isPresent()) {
            UserModel user = optionalUser.get();
            // 2) valida a senha
            if (user.getSenha().equals(loginDto.senha())) {
                // 3) retorna id e nome em um DTO de resposta
                return ResponseEntity.ok(new UserResponse(user.getId(), user.getNome()));
            } else {
                return ResponseEntity.status(401).body("Senha inválida");
            }
        } else {
            return ResponseEntity.status(404).body("Usuário não encontrado");
        }
    }

    // DTO de resposta embutido
    private record UserResponse(UUID id, String nome) {}
}
