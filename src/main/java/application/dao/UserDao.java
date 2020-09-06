package application.dao;

import application.model.security.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDao extends CrudRepository<User, Long> {
    User findByUsername(String username);
    boolean existsByUsername(String username);

    @Query(value = "SELECT username FROM users", nativeQuery = true)
    List<String> findAllUsernames();
}
