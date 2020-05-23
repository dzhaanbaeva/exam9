package kg.exam9.forum.service;

import kg.exam9.forum.dto.ThemeDTO;
import kg.exam9.forum.model.Theme;
import kg.exam9.forum.repository.ThemeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public Page<ThemeDTO> getTheme(Pageable pageable) {
        return themeRepository.findAll(pageable)
                .map(ThemeDTO::from);
        }

    public Iterable<Theme> getThemes() {
        return themeRepository.findAll();
    }

    public ThemeDTO addTheme(ThemeDTO themeData) {

        Theme theme = Theme.builder()
                .text(themeData.getText())
                .date(LocalDateTime.now())
                .build();


        themeRepository.save(theme);
        return ThemeDTO.from(theme);
    }
}
