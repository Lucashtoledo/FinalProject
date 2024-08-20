package com.docservice.backend.repository;

import com.docservice.backend.entity.Client;
import com.docservice.backend.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByClient(Client client);
}
