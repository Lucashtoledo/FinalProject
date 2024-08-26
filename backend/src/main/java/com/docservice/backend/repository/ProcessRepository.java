package com.docservice.backend.repository;

import com.docservice.backend.entity.Form;
import com.docservice.backend.entity.LegalProcess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProcessRepository extends JpaRepository<LegalProcess, Long> {
    List<LegalProcess> findByClientId(Long clientId);
    Optional<LegalProcess> findById(Long id);
}
