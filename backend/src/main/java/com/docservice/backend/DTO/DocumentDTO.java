package com.docservice.backend.DTO;

public record DocumentDTO(byte[] content, Long id, Long necessaryId, String name) {
}
