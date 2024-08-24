package com.docservice.backend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity
@Table(name = "documents")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;  // Nome do arquivo

    @Lob
    @Column(nullable = false)
    private byte[] content;    // Conteúdo do arquivo

    @ManyToOne
    @JoinColumn(name = "formulario_id")
    private Form form;

    @ManyToOne
    private Client client;

}
