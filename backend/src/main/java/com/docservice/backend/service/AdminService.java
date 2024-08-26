package com.docservice.backend.service;

import com.docservice.backend.DTO.ClientDTO;
import com.docservice.backend.DTO.LegalProcessDTO;
import com.docservice.backend.entity.*;
import com.docservice.backend.repository.AdminRepository;
import com.docservice.backend.repository.ClientRepository;
import com.docservice.backend.repository.FormRepository;
import com.docservice.backend.repository.ProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService {


    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private FormRepository formRepository;


    //--------------------Serviços para o Cliente ------------------------

    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        List<ClientDTO> clientDTO = new ArrayList<>();
        for (Client client : clients) {
            clientDTO.add(toDTO(client));
        }
        return ResponseEntity.status(HttpStatus.OK).body(clientDTO);
    }
    public ClientDTO toDTO(Client client) {
        return new ClientDTO(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getPhone()
        );
    }

    public Optional<Client> getClientByCpf(String cpf) {
        return clientRepository.findByCpf(cpf);
    }

    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    //-----------------------Serviços para o Admin------------------------

    public Optional<Admin> getAdminById(Long id) {
        return adminRepository.findById(id);
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }
    
    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }

    public void updateAdmin(Long id, Admin admin) {
        Optional<Admin> adminOptional = adminRepository.findById(id);
        if (adminOptional.isPresent()) {
            adminOptional.get().setName(admin.getName());
            adminOptional.get().setEmail(admin.getEmail());
            adminOptional.get().setPassword(admin.getPassword());
            adminRepository.save(adminOptional.get());
        }else {
            System.out.println("Admin not found");
        }
    }


    //-----------------------Serviços para processo------------------------

    /*public ResponseEntity<LegalProcess> saveProcess(LegalProcessDTO legalProcessDTO) {
        System.out.println("Numero do processo " + legalProcessDTO.numberProcess());
        System.out.println("Nome do processo " + legalProcessDTO.processName());
        System.out.println("Id do formulario " + legalProcessDTO.formId());
        System.out.println("Id do cliente " + legalProcessDTO.clientId());
        Form form = formRepository.findById(legalProcessDTO.formId()).orElse(null);
        Client client = clientRepository.findById(legalProcessDTO.clientId()).orElse(null);


        if(form != null && client != null) {
            LegalProcess legalProcess = new LegalProcess();
            legalProcess.setForm(form);
            legalProcess.setClient(client);
            legalProcess.setProcessName(legalProcessDTO.processName());
            legalProcess.setNumberProcess(legalProcessDTO.numberProcess());

            processRepository.save(legalProcess);
            return ResponseEntity.status(HttpStatus.CREATED).body(legalProcess);
        }
        return ResponseEntity.notFound().build();
    }

     */
    public ResponseEntity<LegalProcess> createLegalProcess(String processName, int numberProcess, String formName, List<String> necessaryDocs, Client client) {
        // Teste de parâmetros recebidos
        System.out.println("Process Name" + processName);
        System.out.println("Number Process" + numberProcess);
        System.out.println("Form Name: " + formName);
        for (String necessaryDoc : necessaryDocs) {
            System.out.println(necessaryDoc);
        }

       try{
           Form form = new Form();
           form.setName(formName);

           // Cria os NecessaryDocs
           List<NecessaryDoc> necessaryDocList = necessaryDocs.stream()
                   .map(docName -> {
                       NecessaryDoc necessaryDoc = new NecessaryDoc();
                       necessaryDoc.setDocumentName(docName);

                       // Relaciona cada NecessaryDoc com um novo Document
                       Document document = new Document();
                       document.setFileName(docName + ".pdf");
                       necessaryDoc.setDocument(document);

                       // Associar o NecessaryDoc ao Form
                       necessaryDoc.setForm(form); // Adicione esta linha

                       return necessaryDoc;
                   })
                   .collect(Collectors.toList());

           form.setNecessaryDocs(necessaryDocList);
           // Persiste o formulário para obter o ID gerado
           formRepository.save(form);


           // Cria o processo e associa o formulário
           LegalProcess legalProcess = new LegalProcess();
           legalProcess.setProcessName(processName);
           legalProcess.setNumberProcess(numberProcess);
           legalProcess.setClient(client);
           legalProcess.setForm(form);

           // Persiste o processo
           processRepository.save(legalProcess);
           return ResponseEntity.status(HttpStatus.CREATED).body(legalProcess);
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.CREATED).body(null);
       }
    }

    public ResponseEntity<List<LegalProcess>> getProcessesByClientId(Long clientId) {
        return ResponseEntity.status(HttpStatus.OK).body(processRepository.findByClientId(clientId));
    }

}


