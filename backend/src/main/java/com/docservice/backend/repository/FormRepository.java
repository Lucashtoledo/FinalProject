package com.docservice.backend.repository;

import com.docservice.backend.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormRepository extends JpaRepository<Form, Long> {
}
