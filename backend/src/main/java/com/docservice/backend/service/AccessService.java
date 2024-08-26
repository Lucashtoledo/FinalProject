package com.docservice.backend.service;

import com.docservice.backend.entity.Access;
import com.docservice.backend.repository.AccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AccessService {

    @Autowired
    private AccessRepository accessRepository;

    public String getRole(String email) {
       return accessRepository.findAccessByEmail(email).getRole();
    }

    public void setRole(String email, String role) {
        Access access = accessRepository.findAccessByEmail(email);
        access.setRole(role);
    }

    public ResponseEntity<Boolean> testAccess(String email, String password, String role) {
        Access access = accessRepository.findAccessByEmail(email);
        if (access.getPassword().equals(password) && access.getRole().equals(role)) {
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        return ResponseEntity.status(HttpStatus.OK).body(false);
    }

    public Access getAccess(String email, String password) {
        return accessRepository.findAccessByEmail(email);
    }

    public void setAccess(Access access) {
        accessRepository.save(access);
    }


    public ResponseEntity<Void> deleteAccess(Long id) {
        accessRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Access> saveAccess(Access access){
        accessRepository.save(access);
        return ResponseEntity.status(HttpStatus.CREATED).body(access);
    }


}
