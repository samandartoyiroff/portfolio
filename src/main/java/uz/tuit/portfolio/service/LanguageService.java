package uz.tuit.portfolio.service;

import org.springframework.http.ResponseEntity;

public interface LanguageService {
    ResponseEntity<?> search(String query);

    ResponseEntity<?> findAll();


}
