package com.docservice.backend.repository;

import com.docservice.backend.entity.NecessaryDoc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NecessaryDocRepository extends JpaRepository<NecessaryDoc, Long> {
    List<NecessaryDoc> findByFormId(Long formId);
}
