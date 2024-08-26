package com.docservice.backend.controller;

import com.docservice.backend.entity.Client;
import com.docservice.backend.service.AccessService;
import com.docservice.backend.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/access")
@CrossOrigin(origins = "http://localhost:4200")
public class AccessController {

    @Autowired
    private AccessService accessService;
    @Autowired
    private ClientService clientService;


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccess(@PathVariable Long id) {
        return accessService.deleteAccess(id);
    }

    @GetMapping("/login")
    public ResponseEntity<Boolean> consultAccess(@RequestParam String email, @RequestParam String password, @RequestParam String role) {
        return accessService.testAccess(email, password, role);
    }

    @GetMapping("/client")
    public ResponseEntity<Client> findClientByEmail(@RequestParam String email) {
        Client client = clientService.getClientByEmail(email).orElse(null);
        return ResponseEntity.ok(client);
    }
}
