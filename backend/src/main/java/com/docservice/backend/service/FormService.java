package com.docservice.backend.service;

import com.docservice.backend.entity.Form;
import com.docservice.backend.entity.LegalProcess;
import com.docservice.backend.repository.FormRepository;
import com.docservice.backend.repository.ProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class FormService {

    @Autowired
    private FormRepository formRepository;
    @Autowired
    private ProcessRepository processRepository;

    public List<Form> getAllForms() {
        return formRepository.findAll();
    }

    public Form saveForm(Form form) {
        return formRepository.save(form);
    }

    public void deleteForm(Long id) {
        formRepository.deleteById(id);
    }

    public Long getProcessById(Long id) {
        try{
            Optional<LegalProcess> process = processRepository.findById(id);
            Long formId = process.get().getForm().getId();
            return formId;
        }catch (Exception e){
            return null;
        }

    }



    public ResponseEntity<List<Form>> getFormById(Long id) {
        List<Form> forms = formRepository.findAll();
        List<Form> formById = new ArrayList<>();
        for (Form form : forms) {
            if (form.getId().equals(id)) {
                formById.add(form);
            }
        }
        if (!formById.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(formById);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(formById);
        }
    }
}

