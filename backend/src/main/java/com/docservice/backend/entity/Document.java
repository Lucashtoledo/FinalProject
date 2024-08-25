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

    @Column(nullable = true)
    private String fileName;  // Nome do arquivo

    @Lob
    @Column(nullable = true)
    private byte[] content;    // Conteúdo do arquivo

    @OneToOne(mappedBy = "document")
    private NecessaryDoc necessaryDoc;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;


}
