package com.docservice.backend.service;

import com.docservice.backend.entity.Client;
import com.docservice.backend.entity.Document;
import com.docservice.backend.entity.Form;
import com.docservice.backend.repository.ClientRepository;
import com.docservice.backend.repository.FormRepository;
import com.docservice.backend.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private ClientRepository clientRepository;

    public Document uploadDocument(Long formId, Long clientId, MultipartFile file) throws IOException {
        Form form = formRepository.findById(formId).orElseThrow(() -> new RuntimeException("Form not found"));
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Client not found"));

        // Criando o objeto Document
        Document document = new Document();
        document.setFileName(file.getOriginalFilename());
        document.setContent(file.getBytes());  // Armazenando o conteúdo do arquivo no banco
        document.setForm(form);

        return documentRepository.save(document);
    }

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    public Document getDocumentById(Long documentId) {
        return documentRepository.findById(documentId).orElseThrow(() -> new RuntimeException("Document not found"));
    }

    public void deleteDocument(Long documentId) {
        documentRepository.deleteById(documentId);
    }

    // Método para obter todos os documentos de um cliente específico
    public List<Document> getClientDocuments(Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Client not found"));
        return documentRepository.findByClient(client);
    }

}
