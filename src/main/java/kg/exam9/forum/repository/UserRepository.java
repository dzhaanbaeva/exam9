package kg.exam9.forum.repository;

import kg.exam9.forum.dto.UserDTO;
import kg.exam9.forum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    User findUserByEmail(UserDTO email);
}

