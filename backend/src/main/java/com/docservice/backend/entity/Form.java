package com.docservice.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "forms")
@AllArgsConstructor
@NoArgsConstructor
public class Form {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "form", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents = new ArrayList<>();  // Lista de documentos ligad

    public Form(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
