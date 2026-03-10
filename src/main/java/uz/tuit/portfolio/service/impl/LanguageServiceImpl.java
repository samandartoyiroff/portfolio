package uz.tuit.portfolio.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.tuit.portfolio.domain.Language;
import uz.tuit.portfolio.dto.response.LanguageResponseDto;
import uz.tuit.portfolio.mapper.LanguageMapper;
import uz.tuit.portfolio.repository.LanguageRepository;
import uz.tuit.portfolio.service.LanguageService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;
    private final LanguageMapper languageMapper;


    @Override
    public ResponseEntity<?> search(String query) {

        List<Language> languages = languageRepository.search(query);

        List<LanguageResponseDto> languagesList = languages.stream().map(languageMapper::languageToLanguageDto).toList();

        return ResponseEntity.ok(languagesList);
    }

    @Override
    public ResponseEntity<?> findAll() {

        List<Language> languages = languageRepository.findAll();

        List<LanguageResponseDto> list = languages.stream().map(language -> languageMapper.languageToLanguageDto(language)).toList();

        return ResponseEntity.ok(list);

    }
}
