package com.docservice.backend.controller;

import com.docservice.backend.entity.Document;
import com.docservice.backend.entity.Form;
import com.docservice.backend.service.DocumentService;
import com.docservice.backend.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "http://localhost:4200")
public class ClientController {

    @Autowired
    private FormService formService;

    @Autowired
    private DocumentService documentService;

    // Listar todos os formulários disponíveis para o cliente
    @GetMapping("/forms")
    public ResponseEntity<List<Form>> getAllForms() {
        List<Form> forms = formService.getAllForms();
        return ResponseEntity.ok(forms);
    }

    // Enviar documentos
    @PostMapping("/forms/{formId}/documents")
    public ResponseEntity<Document> uploadDocument(@PathVariable Long formId,
                                                   @RequestParam Long clientId, // Adicionado clientId aqui
                                                   @RequestParam("file") MultipartFile file) {
        try {
            Document document = documentService.uploadDocument(formId, clientId, file);
            return ResponseEntity.ok(document);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null); // Retorne um erro apropriado
        }
    }

    // Listar os documentos enviados pelo cliente
    @GetMapping("/documents")
    public ResponseEntity<List<Document>> getClientDocuments(@RequestParam Long clientId) {
        List<Document> documents = documentService.getClientDocuments(clientId);
        return ResponseEntity.ok(documents);
    }
}
