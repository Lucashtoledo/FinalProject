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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id", nullable = false)
    private Form form;  // Relacionamento com o formulário

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;  // Relacionamento com o cliente que enviou o documento

    @Column(nullable = true)
    private String uploadDate;  // Data de envio do documento

}
