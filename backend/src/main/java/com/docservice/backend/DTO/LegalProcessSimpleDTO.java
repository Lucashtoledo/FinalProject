package com.docservice.backend.DTO;

public record LegalProcessSimpleDTO(Long id, String processName){
    public LegalProcessSimpleDTO(Long id, String processName) {
        this.id = id;
        this.processName = processName;
    }
}
