package com.docservice.backend.repository;

import com.docservice.backend.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
