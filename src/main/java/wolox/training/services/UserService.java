package wolox.training.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wolox.training.authentication.IAuthentication;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.exceptions.UserIdMismatchException;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.exceptions.UserOldPasswordMismatchException;
import wolox.training.models.Book;
import wolox.training.models.CurrentUser;
import wolox.training.models.PasswordReset;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IAuthentication authentication;

    public CurrentUser authenticatedCurrentUser() {
        return new CurrentUser(authentication.getAuthentication().getName());
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User update(User user, Long id) {
        if (user.getId() != id) {
            throw new UserIdMismatchException();
        }

        User userFound = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        user.setPassword(userFound.getPassword());

        userRepository.findById(id).orElseThrow(BookNotFoundException::new);
        return userRepository.save(user);
    }

    public void passwordReset(Long id, PasswordReset passwords) {
        User userFound = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        if (passwordEncoder.matches(passwords.getOldPassword(), userFound.getPassword())) {
            userFound.setPassword(passwordEncoder.encode(passwords.getNewPassword()));
            userRepository.save(userFound);
        } else {
            throw new UserOldPasswordMismatchException();
        }
    }

    public void delete(Long id) {
        userRepository.findById(id).orElseThrow(BookNotFoundException::new);
        userRepository.deleteById(id);
    }

    public User addBook(Long idUser, Long idBook) {
        User userFound = userRepository.findById(idUser).orElseThrow(UserNotFoundException::new);
        Book bookFound = bookRepository.findById(idBook).orElseThrow(BookNotFoundException::new);
        userFound.addBook(bookFound);
        return userRepository.save(userFound);
    }

    public User removeBook(Long idUser, Long idBook) {
        User userFound = userRepository.findById(idUser).orElseThrow(UserNotFoundException::new);
        Book bookFound = bookRepository.findById(idBook).orElseThrow(BookNotFoundException::new);
        userFound.removeBook(bookFound);
        return userRepository.save(userFound);
    }

}
