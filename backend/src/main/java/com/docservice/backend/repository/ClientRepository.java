package com.docservice.backend.repository;

import com.docservice.backend.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
