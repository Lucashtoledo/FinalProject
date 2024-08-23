package com.docservice.backend.service;

import com.docservice.backend.entity.Admin;
import com.docservice.backend.entity.Client;
import com.docservice.backend.repository.AdminRepository;
import com.docservice.backend.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {


    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ClientRepository clientRepository;


    //--------------------Serviços para o Cliente ------------------------
    public List<Client> getAllClients() {
        return clientRepository.findAll();
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


}
