package kg.exam9.forum.dto;

import kg.exam9.forum.model.Theme;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ThemeDTO {
    public static ThemeDTO from(Theme topic){
        return ThemeDTO.builder()
                .id(topic.getId())
                .text(topic.getText())
                .date(LocalDateTime.now())
                .author(UserDTO.from(topic.getAuthor()))
                .build();
    }

    private Integer id;
    private String text;
    private LocalDateTime date = LocalDateTime.now();
    private UserDTO author;

}
