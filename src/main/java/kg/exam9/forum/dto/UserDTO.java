package kg.exam9.forum.dto;


import kg.exam9.forum.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private int id;
    private String fullname;
    private String email;

    public static UserDTO from(User user) {
        return builder()
                .id(user.getId())
                .fullname(user.getFullname())
                .email(user.getEmail())
                .build();
    }
}
