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

public class Form {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String name;

    @ElementCollection
    private List<String> necessaryDoc;

    public Form(){
        necessaryDoc = new ArrayList<>();
    }
}
