package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    /**
     * Method that gets all users
     *
     * @return all {@link User}
     */
    @GetMapping
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Method that obtain a user by id
     *
     * @param id: User identifier (Long)
     * @return {@link User}
     */
    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    /**
     * User saving method
     *
     * @param user: Request body ({@link User})
     * @return {@link User}
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User save(@RequestBody User user) {
        return userRepository.save(user);
    }

    /**
     * User updating method
     *
     * @param user: Request body ({@link User})
     * @param id:   User identifier (Long)
     * @return {@link User}
     */
    @PutMapping("/{id}")
    public User update(@RequestBody User user, @PathVariable Long id) {
        User userFound = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return userRepository.save(user);
    }

    /**
     * User deleting method
     *
     * @param id: User identifier (Long)
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

        userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        userRepository.deleteById(id);

    }

    /**
     * Method that adds a book
     *
     * @param idUser: User identifier (Long)
     * @param idBook: Book identifier (Long)
     * @return {@link User}
     */
    @PutMapping("/{idUser}/books/{idBook}")
    public User addBook(@PathVariable Long idUser, @PathVariable Long idBook) {

        User userFound = userRepository.findById(idUser)
                .orElseThrow(UserNotFoundException::new);

        Book bookFound = bookRepository.findById(idBook)
                .orElseThrow(BookNotFoundException::new);

        userFound.setBook(bookFound);

        return userRepository.save(userFound);

    }

    /**
     * Method that removes a book
     *
     * @param idUser: User identifier (Long)
     * @param idBook: Book identifier (Long)
     * @return {@link User}
     */
    @DeleteMapping("/{idUser}/books/{idBook}")
    public User removeBook(@PathVariable Long idUser, @PathVariable Long idBook) {

        User userFound = userRepository.findById(idUser)
                .orElseThrow(UserNotFoundException::new);

        Book bookFound = bookRepository.findById(idBook)
                .orElseThrow(BookNotFoundException::new);

        userFound.removeBook(bookFound);

        return userRepository.save(userFound);

    }

}
