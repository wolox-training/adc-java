package wolox.training.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import wolox.training.client.delegate.OpenLibraryDelegate;
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.mappers.BookMapper;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

import java.util.Collections;
import java.util.Map;
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

    @Autowired
    private ObjectMapper objectMapper;

    public Iterable<Book> findAll(Map<String,String> params) {
        Pageable pageable = getPageFromMap(params);
        if (params.size() == 0) return bookRepository.findAll(pageable);
        Book bookToFind = objectMapper.convertValue(params, Book.class);
        if (StringUtils.isNotEmpty(bookToFind.getIsbn())) filterBookByIsbn(bookToFind.getIsbn());
        ExampleMatcher customExampleMatcher = ExampleMatcher.matchingAny();
        Example<Book> example = Example.of(bookToFind, customExampleMatcher);
        return bookRepository.findAll(example, pageable);
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

    private Pageable getPageFromMap(Map<String,String> params) {
        int page = Integer.parseInt(params.getOrDefault("page", "0"));
        int size = Integer.parseInt(params.getOrDefault("size", "5"));
        String sort = params.getOrDefault("sort", "id");
        params.remove("page"); params.remove("size"); params.remove("sort");
        return PageRequest.of(page, size, Sort.by(sort));
    }
}
