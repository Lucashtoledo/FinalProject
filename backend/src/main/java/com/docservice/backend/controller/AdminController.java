package com.docservice.backend.controller;

import com.docservice.backend.DTO.ClientDTO;
import com.docservice.backend.DTO.DocumentDTO;
import com.docservice.backend.DTO.LegalProcessDTO;
import com.docservice.backend.DTO.LegalProcessSimpleDTO;
import com.docservice.backend.entity.*;
import com.docservice.backend.repository.ClientRepository;
import com.docservice.backend.repository.DocumentRepository;
import com.docservice.backend.repository.ProcessRepository;
import com.docservice.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    @Autowired
    private AccessService accessService;

    @Autowired
    private FormService formService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private DocumentRepository documentRepository;

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

    // Listar todos os admins
    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmin() {
        List<Admin> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/client")
    public ResponseEntity<List<ClientDTO>> getAllClient() {
        return adminService.getAllClients();
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

    // Atualizar cliente
    @PutMapping("/client/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client client) {
        Optional<Client> updateClient = clientService.getClientById(id);
        if (updateClient.isPresent()) {
            clientService.updateClient(id, client);
            return ResponseEntity.ok(client);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    // Adicionar novo processo
   // @PostMapping("/newprocess")
   // public ResponseEntity<LegalProcess> newProcess(@RequestBody LegalProcessDTO legalProcessDTO) {
     //   return adminService.saveProcess(legalProcessDTO);
    //}



    @GetMapping("/forms/{id}")
    public ResponseEntity<List<Form>> getFormById(@PathVariable Long id) {
        return formService.getFormById(id);
    }

    @GetMapping("/process/{id}")
    public ResponseEntity<List<LegalProcessSimpleDTO>> getProcessByClientId(@PathVariable Long id) {
        List<LegalProcess> processes = processRepository.findByClientId(id);
        List<LegalProcessSimpleDTO> processDTOs = processes.stream()
                .map(process -> new LegalProcessSimpleDTO(process.getId() ,process.getProcessName()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(processDTOs);
    }

    @PostMapping("/newprocess")
    public ResponseEntity<LegalProcess> createLegalProcess(@RequestBody CreateProcessRequest request) {
        // Obtem o Id e transforma em um objeto Cliente
        Client client = new Client();
        client.setId(request.getClientId());

        try{
            adminService.createLegalProcess(
                    request.getProcessName(),
                    request.getNumberProcess(),
                    request.getFormName(),
                    request.getNecessaryDocs(),
                    client);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        }

    }

    @GetMapping("/documents/{id}")
    public ResponseEntity<List<DocumentDTO>> getDocumentsByProcessId(@PathVariable Long id){
        return documentService.getDocByProcessId(id);
    }

    @PostMapping("/access")
    public ResponseEntity<Access> saveAccess(@RequestBody Access access) {
        return accessService.saveAccess(access);
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFull(@RequestParam("docId") Long docId) {
        return documentService.getDocumentFileById(docId);
    }

    // Envia um documento
    @PostMapping("/upload")
    public ResponseEntity<Void> uploadFile(@RequestParam("docId") Long docId,
                                           @RequestParam("file") MultipartFile file) {
        try {
            // Converter MultipartFile para byte[]
            byte[] fileBytes = file.getBytes();

            // Chamar o serviço para salvar o documento
            documentService.uploadFile(docId, fileBytes);

            // Retorna um status de sucesso
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/download-part")
    public ResponseEntity<byte[]> downloadPart(
            @RequestParam Long docId,
            @RequestParam int partIndex) {

        byte[] part = documentService.getDocumentPart(docId, partIndex);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(part);
    }

}

