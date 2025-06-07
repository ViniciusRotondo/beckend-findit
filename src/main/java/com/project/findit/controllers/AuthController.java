package com.project.findit.controllers;

import com.project.findit.dtos.UserLoginDto;
import com.project.findit.models.UserModel;
import com.project.findit.models.OrganizerModel;
import com.project.findit.services.UserService;
import com.project.findit.services.OrganizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrganizerService organizerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto loginDto) {
        // 1) tenta buscar usuário
        Optional<UserModel> optionalUser = userService.findByEmail(loginDto.email());

        if (optionalUser.isPresent()) {
            UserModel user = optionalUser.get();
            if (passwordEncoder.matches(loginDto.senha(), user.getSenha())) {
                return ResponseEntity.ok(new AuthResponse(user.getId(), user.getNome(), "USUARIO", null));
            } else {
                return ResponseEntity.status(401).body("Senha inválida");
            }
        }

        // 2) tenta buscar organizador
        Optional<OrganizerModel> optionalOrganizer = organizerService.findByEmail(loginDto.email());

        if (optionalOrganizer.isPresent()) {
            OrganizerModel organizer = optionalOrganizer.get();
            if (passwordEncoder.matches(loginDto.senha(), organizer.getSenha())) {
                return ResponseEntity.ok(new AuthResponse(organizer.getIdOrganizador(), organizer.getNome(),"ORGANIZADOR", organizer.getCpf()));
            } else {
                return ResponseEntity.status(401).body("Senha inválida");
            }
        }

        return ResponseEntity.status(404).body("Usuário ou organizador não encontrado");
    }

    private record AuthResponse(UUID id, String nome, String tipo, String cpf) {}
}