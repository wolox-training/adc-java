package wolox.training.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wolox.training.client.delegate.OpenLibraryDelegate;
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.mappers.BookMapper;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    private static final Long BOOK_ID = 0L;
    private static final Long INVALID_BOOK_ID = 1L;

    private Book bookTest;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private OpenLibraryDelegate openLibraryDelegate;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookTest = new Book();
        bookTest.setGenre("Horror");
        bookTest.setAuthor("Juan Osorio");
        bookTest.setImage("horror.jpg");
        bookTest.setTitle("Title");
        bookTest.setSubtitle("Subtitle");
        bookTest.setPublisher("Publisher");
        bookTest.setYear("2020");
        bookTest.setPages(90);
        bookTest.setIsbn("0909-1234-6789-X");
    }

    @Test
    void whenFindAll_thenReturnAllBooks() {
        when(bookRepository.findAll()).thenReturn(Collections.singletonList(bookTest));

        Iterable<Book> books = bookService.findAll(null);

        assertThat(books.iterator().hasNext()).isTrue();
        assertThat(books.iterator().next().getIsbn()).isEqualTo(bookTest.getIsbn());
        assertThat(books.iterator().next().getAuthor()).isEqualTo(bookTest.getAuthor());
        assertThat(books.iterator().next().getGenre()).isEqualTo(bookTest.getGenre());
        assertThat(books.iterator().next().getImage()).isEqualTo(bookTest.getImage());
    }

    @Test
    void whenFindById_thenReturnBook() {
        when(bookRepository.findById(any())).thenReturn(Optional.of(bookTest));

        Book book = bookService.findById(BOOK_ID);

        assertThat(book.getIsbn()).isEqualTo(bookTest.getIsbn());
        assertThat(book.getAuthor()).isEqualTo(bookTest.getAuthor());
        assertThat(book.getGenre()).isEqualTo(bookTest.getGenre());
        assertThat(book.getImage()).isEqualTo(bookTest.getImage());
    }

    @Test
    void whenFindById_thenReturnBookNotFound() {
        when(bookRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.findById(BOOK_ID))
                .isInstanceOf(BookNotFoundException.class);
    }

    @Test
    void whenSave_thenReturnSavedBook() {
        when(bookRepository.save(any())).thenReturn(bookTest);

        Book book = bookService.save(bookTest);

        assertThat(book.getIsbn()).isEqualTo(bookTest.getIsbn());
        assertThat(book.getAuthor()).isEqualTo(bookTest.getAuthor());
        assertThat(book.getGenre()).isEqualTo(bookTest.getGenre());
        assertThat(book.getImage()).isEqualTo(bookTest.getImage());
    }

    @Test
    void whenUpdate_thenReturnUpdatedBook() {
        when(bookRepository.findById(any())).thenReturn(Optional.of(bookTest));
        when(bookRepository.save(any())).thenReturn(bookTest);

        Book book = bookService.update(bookTest, BOOK_ID);

        assertThat(book.getIsbn()).isEqualTo(bookTest.getIsbn());
        assertThat(book.getAuthor()).isEqualTo(bookTest.getAuthor());
        assertThat(book.getGenre()).isEqualTo(bookTest.getGenre());
        assertThat(book.getImage()).isEqualTo(bookTest.getImage());
    }

    @Test
    void whenUpdate_thenReturnUpdatedBookPreconditionFailed() {
        assertThatThrownBy(() -> bookService.update(bookTest, INVALID_BOOK_ID))
                .isInstanceOf(BookIdMismatchException.class);
    }

    @Test
    void whenDelete_thenReturnDeletedBook() {
        when(bookRepository.findById(any())).thenReturn(Optional.of(bookTest));
        doNothing().when(bookRepository).deleteById(any());

        bookService.delete(BOOK_ID);

        verify(bookRepository, times(1)).deleteById(BOOK_ID);
    }
}
