package com.docservice.backend.controller;

import com.docservice.backend.entity.Document;
import com.docservice.backend.service.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/documents")
@CrossOrigin(origins = "http://localhost:4200")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    // Listar todos os documentos (administrador)
    @GetMapping
    public ResponseEntity<List<Document>> getAllDocuments() {
        List<Document> documents = documentService.getAllDocuments();
        return ResponseEntity.ok(documents);
    }

    // Obter um documento específico por ID
    @GetMapping("/{documentId}")
    public ResponseEntity<Document> getDocumentById(@PathVariable Long documentId) {
        Document document = documentService.getDocumentById(documentId);
        return ResponseEntity.ok(document);
    }

    // Enviar um documento (cliente)
    @PostMapping("/upload")
    public ResponseEntity<Document> uploadDocument(
            @RequestParam("formId") Long formId,
            @RequestParam("clientId") Long clientId,
            @RequestParam("file") MultipartFile file) throws IOException {
        Document document = documentService.uploadDocument(formId, clientId, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(document);
    }

    // Deletar um documento (administrador)
    @DeleteMapping("/{documentId}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long documentId) {
        documentService.deleteDocument(documentId);
        return ResponseEntity.noContent().build();
    }
}
