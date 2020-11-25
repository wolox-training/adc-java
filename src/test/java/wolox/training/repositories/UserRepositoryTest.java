package wolox.training.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import wolox.training.models.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class UserRepositoryTest {

    private static final String USERNAME = "test.username";

    private User userToSave;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUpData() {
        userToSave = new User();
        userToSave.setUsername(USERNAME);
        userToSave.setName("Test");
        userToSave.setBirthdate(LocalDate.now().minusDays(1));

        userRepository.save(userToSave);
    }

    @Test
    void whenFindAll_thenReturnAllUsers() {

        // Act
        Iterable<User> users = userRepository.findAll();

        // Assert
        assertThat(users.iterator().hasNext()).isTrue();
        assertThat(users.iterator().next()).isEqualToComparingFieldByFieldRecursively(userToSave);

    }

    @Test
    void whenFindByUsername_thenReturnUser() {

        // Act
        User userFound = userRepository.findByUsername(USERNAME).get();

        // Assert
        assertThat(userFound).isEqualToComparingFieldByFieldRecursively(userToSave);

    }

    @Test
    void whenUpdate_thenReturnUpdatedUser() {

        // Act
        User userFoundOld = userRepository.findByUsername(USERNAME).get();
        userFoundOld.setName("Test Test");

        userRepository.save(userFoundOld);
        User userFoundUpdated = userRepository.findByUsername(USERNAME).get();

        // Arrange
        assertThat(userFoundUpdated.getName()).isEqualTo(userFoundOld.getName());
        assertThat(userFoundUpdated.getUsername()).isEqualTo(userFoundOld.getUsername());
        assertThat(userFoundUpdated.getBirthdate()).isEqualTo(userFoundOld.getBirthdate());

    }

    @Test
    void whenDelete_thenReturnZeroBooks() {

        // Act
        User userFound = userRepository.findByUsername(USERNAME).get();

        userRepository.delete(userFound);

        Iterable<User> users = userRepository.findAll();

        // Assert
        assertThat(users.iterator().hasNext()).isFalse();

    }

}