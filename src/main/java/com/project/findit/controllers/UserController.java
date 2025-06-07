package com.project.findit.controllers;

import com.project.findit.models.ChangePasswordRequest;
import com.project.findit.models.UserModel;
import com.project.findit.repositories.UserRepository;
import com.project.findit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/user")
    public UserModel addUserDetails(@RequestBody UserModel userModel){
        return userService.createUser(userModel);
    }

    @GetMapping("/user")
    public List<UserModel> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserModel> getUserDetails (@PathVariable UUID id){
        UserModel userModel = userService.getUserDetails(id).orElse(null);

        if (userModel != null){
            return ResponseEntity.ok().body(userModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<UserModel> updateUserDetails(@PathVariable UUID id, @RequestBody UserModel userModel){
        UserModel upadatedUser = userService.updateUserDatails(id, userModel);

        if(upadatedUser != null){
            return ResponseEntity.ok().body(upadatedUser);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/user/{id}/change-password")
    public ResponseEntity<String> changePassword(
            @PathVariable UUID id,
            @RequestBody ChangePasswordRequest request) {


        System.out.println("Old password recebida: " + request.getOldPassword());
        System.out.println("New password recebida: " + request.getNewPassword());

        boolean success = userService.changePassword(id, request.getOldPassword(), request.getNewPassword());

        if (success) {
            return ResponseEntity.ok("Senha alterada com sucesso.");
        } else {
            return ResponseEntity.badRequest().body("Senha atual incorreta ou usuário não encontrado.");
        }
    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        Optional<UserModel> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            UserModel user = userOpt.get();
            String newPassword = "123456"; // ou gerar uma senha aleatória simples
            user.setSenha(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return ResponseEntity.ok("Senha redefinida para: " + newPassword);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("E-mail não encontrado.");
    }

}
