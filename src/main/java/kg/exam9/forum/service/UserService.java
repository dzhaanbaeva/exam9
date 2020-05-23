package kg.exam9.forum.service;

import kg.exam9.forum.dto.UserDTO;
import kg.exam9.forum.exception.AuthorAlreadyRegisteredException;
import kg.exam9.forum.exception.AuthorNotFoundException;
import kg.exam9.forum.model.AuthorRegisterForm;
import kg.exam9.forum.model.User;
import kg.exam9.forum.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserDTO register(AuthorRegisterForm form) {
        if (repository.existsByEmail(form.getEmail())) {
            throw new AuthorAlreadyRegisteredException();
        }

        var user = User.builder()
                .email(form.getEmail())
                .fullname(form.getName())
                .password(encoder.encode(form.getPassword()))
                .build();

        repository.save(user);

        return UserDTO.from(user);
    }

    public UserDTO getByEmail(String email) {
        var user = repository.findByEmail(email)
                .orElseThrow(AuthorNotFoundException::new);

        return UserDTO.from(user);
    }

    public Iterable<User> getUser(UserDTO email) {
        return (Iterable<User>) repository.findUserByEmail(email);
    }
}
