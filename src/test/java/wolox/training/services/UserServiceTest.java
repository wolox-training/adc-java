package wolox.training.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import wolox.training.exceptions.UserIdMismatchException;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.models.User;
import wolox.training.repositories.UserRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private static final Long USER_ID = 0L;
    private static final Long INVALID_USER_ID = 1L;
    private static final String USER_PASS = "QwErTy++";

    private User userTest;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        userTest = new User();
        userTest.setUsername("test.username");
        userTest.setPassword(USER_PASS);
        userTest.setName("Test");
        userTest.setBirthdate(LocalDate.now().minusYears(1));
    }

    @Test
    void whenFindAll_thenReturnAllUsers() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(userTest));

        Iterable<User> users = userService.findAll();

        assertThat(users.iterator().hasNext()).isTrue();
        assertThat(users.iterator().next().getUsername()).isEqualTo(userTest.getUsername());
        assertThat(users.iterator().next().getName()).isEqualTo(userTest.getName());
    }

    @Test
    void whenFindById_thenReturnUser() {
        when(userRepository.findById(any())).thenReturn(Optional.of(userTest));

        User user = userService.findById(USER_ID);

        assertThat(user.getUsername()).isEqualTo(userTest.getUsername());
        assertThat(user.getName()).isEqualTo(userTest.getName());
    }

    @Test
    void whenFindById_thenReturnUserNotFound() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findById(USER_ID))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void whenSave_thenReturnSavedUser() {
        when(userRepository.save(any())).thenReturn(userTest);
        when(passwordEncoder.encode(any())).thenReturn(USER_PASS);

        User user = userService.create(userTest);

        assertThat(user.getUsername()).isEqualTo(userTest.getUsername());
        assertThat(user.getName()).isEqualTo(userTest.getName());
    }

    @Test
    void whenUpdate_thenReturnUpdatedUser() {
        when(userRepository.findById(any())).thenReturn(Optional.of(userTest));
        when(userRepository.save(any())).thenReturn(userTest);

        User user = userService.update(userTest, USER_ID);

        assertThat(user.getUsername()).isEqualTo(userTest.getUsername());
        assertThat(user.getName()).isEqualTo(userTest.getName());
    }

    @Test
    void whenUpdate_thenReturnUpdatedUserPreconditionFailed() {
        assertThatThrownBy(() -> userService.update(userTest, INVALID_USER_ID))
                .isInstanceOf(UserIdMismatchException.class);
    }

    @Test
    void whenDelete_thenReturnDeletedUser() {
        when(userRepository.findById(any())).thenReturn(Optional.of(userTest));
        doNothing().when(userRepository).deleteById(any());

        userService.delete(USER_ID);

        verify(userRepository, times(1)).deleteById(USER_ID);
    }
}
