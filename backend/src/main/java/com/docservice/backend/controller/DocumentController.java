package com.docservice.backend.controller;

import com.docservice.backend.entity.Document;
import com.docservice.backend.service.DocumentService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Arrays;
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

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable Long id, @RequestHeader(value = "Range", required = false) String rangeHeader) {
        Document document = documentService.getDocumentById(id);

        if (document == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        byte[] content = document.getContent();
        int fileSize = content.length;

        // Definindo o tamanho do bloco em 4 MB
        int chunkSize = 4 * 1024 * 1024;

        // Obtendo o início e fim da parte solicitada
        int start = 0;
        int end = Math.min(chunkSize, fileSize) - 1;

        if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
            String[] ranges = rangeHeader.replace("bytes=", "").split("-");
            start = Integer.parseInt(ranges[0]);
            if (ranges.length > 1) {
                end = Integer.parseInt(ranges[1]);
            } else {
                end = Math.min(start + chunkSize - 1, fileSize - 1);
            }
        }

        if (start > fileSize - 1) {
            return ResponseEntity.status(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE).build();
        }

        end = Math.min(end, fileSize - 1);
        byte[] data = Arrays.copyOfRange(content, start, end + 1);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getFileName() + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
        headers.add(HttpHeaders.ACCEPT_RANGES, "bytes");
        headers.add(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + fileSize);

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .headers(headers)
                .body(data);
    }
}
