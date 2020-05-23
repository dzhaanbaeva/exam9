package kg.exam9.forum.model;


import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@Table(name = "theme")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column
    private String text;

    @NotNull
    @Column(length = 128)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;


}
