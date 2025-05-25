package com.project.findit.controllers;

import com.project.findit.services.DataImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/import")
public class AdminController {

    @Autowired
    private DataImportService dataImportService;

    @PostMapping
    public ResponseEntity<?> importarDados() {
        dataImportService.importDataFromIBGE();
        return ResponseEntity.ok("Dados importados com sucesso!");
    }
}