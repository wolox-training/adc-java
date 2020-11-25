package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.PasswordReset;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;
import wolox.training.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Method that gets all users
     *
     * @return all {@link User}
     */
    @GetMapping
    public Iterable<User> findAll() {
        return userService.findAll();
    }

    /**
     * Method that obtain a user by id
     *
     * @param id: User identifier (Long)
     * @return {@link User}
     */
    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        return userService.findById(id);
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
        return userService.create(user);
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
        return userService.update(user, id);
    }

    /**
     * Password update method
     *
     * @param id:        User identifier (Long)
     * @param passwords: Password request ({@link PasswordReset})
     */
    @PutMapping("/{id}/password")
    public void passwordReset(@PathVariable Long id, @RequestBody PasswordReset passwords) {
        userService.passwordReset(id, passwords);
    }

    /**
     * User deleting method
     *
     * @param id: User identifier (Long)
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
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
        return userService.addBook(idUser, idBook);
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
        return userService.removeBook(idUser, idBook);
    }

}
