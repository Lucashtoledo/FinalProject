package com.docservice.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class LegalProcess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String processName;
    @Column(nullable = true)
    private int numberProcess;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonBackReference
    private Client client;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "formulario_id")
    private Form form;


}
