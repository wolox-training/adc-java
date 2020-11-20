package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public Iterable findAll() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public Book findOne(@PathVariable Long id) {
        try {
            return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        } catch (BookNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Book not found", e);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        try {
            bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        } catch (BookNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Book not found", e);
        }
        bookRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
        try {
            bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        } catch (BookNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Book not found", e);
        }
        return bookRepository.save(book);
    }
}
