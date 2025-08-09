package app.jwt.repo;

import app.jwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByUsername(String username);

}
