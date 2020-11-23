package wolox.training.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wolox.training.models.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Method that gets user by his username
     *
     * @param username: User's username (String)
     * @return a {@link User}
     */
    Optional<User> findByUsername(String username);

}
