package kg.exam9.forum.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE) @NoArgsConstructor
@Entity
@Table(name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Theme topic;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @NotBlank
    @Column
    private String answer;

    @NotBlank
    @Column
    private LocalDateTime dateAnswer;


}
