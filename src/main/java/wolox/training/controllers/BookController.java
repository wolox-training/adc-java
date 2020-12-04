package wolox.training.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.services.BookService;

import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * Method that gets all books
     *
     * @return all {@link Book}
     */
    @GetMapping
    @ApiOperation(value = "Return all books", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all books")
    })
    public Iterable findAll(@RequestParam Map<String,String> params) {
        return bookService.findAll(params);
    }

    /**
     * Method that obtain a book by id
     *
     * @param id: Book identifier (Long)
     * @return {@link Book}
     */
    @GetMapping("/{id}")
    public Book findOne(@PathVariable Long id) {
        return bookService.findById(id);
    }

    /**
     * Book saving method
     *
     * @param book: Request body ({@link Book})
     * @return {@link Book}
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return bookService.save(book);
    }

    /**
     * Book deleting method
     *
     * @param id: Book identifier (Long)
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    /**
     * Book updating method
     *
     * @param book: Request body ({@link Book})
     * @param id:   Book identifier (Long)
     * @return {@link Book}
     */
    @PutMapping("/{id}")
    public Book update(@RequestBody Book book, @PathVariable Long id) {
        return bookService.update(book, id);
    }
}
