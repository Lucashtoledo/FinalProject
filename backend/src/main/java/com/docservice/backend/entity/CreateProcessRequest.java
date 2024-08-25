package com.docservice.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProcessRequest {
    private String processName;
    private int numberProcess;
    private String formName;
    private List<String> necessaryDocs;
    private Long clientId;
}
