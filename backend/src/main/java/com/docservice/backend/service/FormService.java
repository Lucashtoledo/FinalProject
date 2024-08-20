package com.docservice.backend.service;

import com.docservice.backend.entity.Form;
import com.docservice.backend.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormService {

    @Autowired
    private FormRepository formRepository;

    public List<Form> getAllForms() {
        return formRepository.findAll();
    }

    public Optional<Form> getFormById(Long id) {
        return formRepository.findById(id);
    }

    public Form saveForm(Form form) {
        return formRepository.save(form);
    }

    public void deleteForm(Long id) {
        formRepository.deleteById(id);
    }
}

