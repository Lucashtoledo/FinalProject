package com.docservice.backend.entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LegalProcess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String processName;

    private int numberProcess;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Client client;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "formulario_id")
    private Form form;

}
