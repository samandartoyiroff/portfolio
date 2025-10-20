package uz.tuit.portfolio.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.tuit.portfolio.service.LanguageService;


@RestController
@RequestMapping("/api/v1/language")
@RequiredArgsConstructor
public class LanguageController {

    private final LanguageService languageService;

    @GetMapping("/search")
    public ResponseEntity<?> searchLanguage(
            @RequestParam(name = "query", required = false) String query
    ) {
        return languageService.search(query);
    }

}
