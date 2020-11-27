package wolox.training.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wolox.training.client.delegate.OpenLibraryDelegate;
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.mappers.BookMapper;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private OpenLibraryDelegate openLibraryDelegate;

    @Autowired
    private BookMapper bookMapper;

    public Iterable<Book> findAll(String isbn) {
        if (StringUtils.isNotEmpty(isbn)) {
            return filterBookByIsbn(isbn);
        }
        return bookRepository.findAll();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Book update(Book book, Long id) {
        if (book.getId() != id) {
            throw new BookIdMismatchException();
        }
        bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        return bookRepository.save(book);
    }

    public void delete(Long id) {
        bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        bookRepository.deleteById(id);
    }



    private Iterable<Book> filterBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .map(book -> Collections.singleton(book))
                .orElseGet(() -> (Set<Book>) getOpenLibraryBook(isbn));
    }

    private Iterable<Book> getOpenLibraryBook(String isbn) {
        Book bookFound = openLibraryDelegate.findBookByIsbn(isbn)
                .map(bookInfoDto -> bookMapper.bookInfoDtoToToEntity(isbn, bookInfoDto))
                .orElseThrow(BookNotFoundException::new);
        bookRepository.save(bookFound);
        return Collections.singleton(bookFound);
    }
}
