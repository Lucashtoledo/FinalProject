package com.docservice.backend.repository;

import com.docservice.backend.entity.LegalProcess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessRepository extends JpaRepository<LegalProcess, Long> {
}
