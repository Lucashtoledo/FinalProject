package com.docservice.backend.repository;

import com.docservice.backend.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

        Optional<Client> findByCpf(String cpf);

        Optional<Client> findByEmail(String email);

}
