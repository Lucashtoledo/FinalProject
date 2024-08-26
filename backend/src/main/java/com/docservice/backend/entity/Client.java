package com.docservice.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "clients")
public class Client extends User {

    @Column(unique = true, nullable = false)
    private String cpf;

    @OneToMany(mappedBy = "client")
    @JsonBackReference
    private List<LegalProcess> processos;
}
