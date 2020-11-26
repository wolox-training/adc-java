package wolox.training.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wolox.training.models.User;

import java.time.LocalDate;
import java.util.List;
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

    /**
     * Method that obtain all users if your birthdate be between two date
     *
     * @param maxDate:        Max date ({@link LocalDate})
     * @param minDate:        Min date ({@link LocalDate})
     * @param nameContaining: Name containing of the user (String)
     * @return {@link User} list
     */
    List<User> findByBirthdateLessThanEqualAndBirthdateGreaterThanEqualAndNameContainingIgnoreCase(
            LocalDate maxDate, LocalDate minDate, String nameContaining);

}
