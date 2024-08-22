package com.docservice.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "clients")
public class Client extends User {
    @Column(unique = true, nullable = false)
    private String cpf;
    private String numberProcess;
}
