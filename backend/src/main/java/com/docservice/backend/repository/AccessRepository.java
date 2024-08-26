package com.docservice.backend.repository;

import com.docservice.backend.entity.Access;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessRepository extends JpaRepository<Access, Long> {
    Access findAccessByEmail(String email);
}
