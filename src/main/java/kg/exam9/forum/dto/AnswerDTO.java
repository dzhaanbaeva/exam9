package kg.exam9.forum.dto;


import kg.exam9.forum.model.Answer;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AnswerDTO {

    private int id;
    private ThemeDTO topic;
    private UserDTO author;
    private String answer;
    private LocalDateTime dateAnswer;


    public static AnswerDTO from(Answer answer){
        return AnswerDTO.builder()
                .id(answer.getId())
                .topic(ThemeDTO.from(answer.getTopic()))
                .author(UserDTO.from(answer.getAuthor()))
                .answer(answer.getAnswer())
                .dateAnswer(answer.getDateAnswer())
                .build();
    }
}
