package com.docservice.backend.service;

import com.docservice.backend.DTO.ClientDTO;
import com.docservice.backend.DTO.LegalProcessDTO;
import com.docservice.backend.entity.Admin;
import com.docservice.backend.entity.Client;
import com.docservice.backend.entity.Form;
import com.docservice.backend.entity.LegalProcess;
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

    public ResponseEntity<LegalProcess> saveProcess(LegalProcessDTO legalProcessDTO) {
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
}


