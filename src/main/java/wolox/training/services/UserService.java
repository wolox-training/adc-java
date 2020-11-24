package wolox.training.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User update(User user, Long id) {
        userRepository.findById(id).orElseThrow(BookNotFoundException::new);
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.findById(id).orElseThrow(BookNotFoundException::new);
        userRepository.deleteById(id);
    }

    public User addBook(Long idUser, Long idBook) {
        User userFound = userRepository.findById(idUser).orElseThrow(UserNotFoundException::new);
        Book bookFound = bookRepository.findById(idBook).orElseThrow(BookNotFoundException::new);
        userFound.setBook(bookFound);
        return userRepository.save(userFound);
    }

    public User removeBook(Long idUser, Long idBook) {
        User userFound = userRepository.findById(idUser).orElseThrow(UserNotFoundException::new);
        Book bookFound = bookRepository.findById(idBook).orElseThrow(BookNotFoundException::new);
        userFound.removeBook(bookFound);
        return userRepository.save(userFound);
    }

}
