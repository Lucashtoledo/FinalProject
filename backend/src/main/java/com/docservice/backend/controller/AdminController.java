package com.docservice.backend.controller;

import com.docservice.backend.entity.Admin;
import com.docservice.backend.entity.Client;
import com.docservice.backend.entity.Document;
import com.docservice.backend.entity.Form;
import com.docservice.backend.repository.ClientRepository;
import com.docservice.backend.service.AdminService;
import com.docservice.backend.service.ClientService;
import com.docservice.backend.service.DocumentService;
import com.docservice.backend.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {


    @Autowired
    private FormService formService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

    // Criar um novo formulário
    @PostMapping("/forms")
    public ResponseEntity<Form> createForm(@RequestBody Form form) {
        Form createdForm = formService.saveForm(form);
        return ResponseEntity.ok(createdForm);
    }

    // Listar todos os formulários
    @GetMapping("/forms")
    public ResponseEntity<List<Form>> getAllForms() {
        List<Form> forms = formService.getAllForms();
        return ResponseEntity.ok(forms);
    }

    // Excluir um formulário
    @DeleteMapping("/forms/{id}")
    public ResponseEntity<Void> deleteForm(@PathVariable Long id) {
        formService.deleteForm(id);
        return ResponseEntity.noContent().build();
    }

    // Listar todos os documentos
    @GetMapping("/documents")
    public ResponseEntity<List<Document>> getAllDocuments() {
        List<Document> documents = documentService.getAllDocuments();
        return ResponseEntity.ok(documents);
    }

    // Excluir um documento
    @DeleteMapping("/documents/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }

    // Adicionar novo admin
    @PostMapping("/newadmin")
    public ResponseEntity<Admin> newAdmin(@RequestBody Admin newadmin) {
        Admin admin = adminService.saveAdmin(newadmin);
        return ResponseEntity.ok(admin);
    }

    // Adicionar cliente
    @PostMapping("/newclient")
    public ResponseEntity<Client> newClient(@RequestBody Client newClient) {
        Client client = adminService.saveClient(newClient);
        return ResponseEntity.ok(client);
    }

    // Listar todos os admin
    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmin() {
        List<Admin> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/client")
    public ResponseEntity<List<Client>> getAllClient() {
        List<Client> clients = adminService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    // Excluir administrador
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/client/{cpf}")
    public ResponseEntity<Optional<Client>> findClientById(@PathVariable String cpf) {
        Optional<Client> client = adminService.getClientByCpf(cpf);
        return ResponseEntity.ok(client);
    }

    // Atualizar administrador
    @PutMapping("/admin/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @RequestBody Admin admin) {
        Optional<Admin> updateAdmin = adminService.getAdminById(id);
        if (updateAdmin.isPresent()) {
            adminService.updateAdmin(id, admin);
            return ResponseEntity.ok(admin);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

}
