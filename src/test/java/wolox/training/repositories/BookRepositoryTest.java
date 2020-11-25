package wolox.training.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import wolox.training.models.Book;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class BookRepositoryTest {

    private static final int PAGE_NUMBER = 90;
    private static final String HORROR_GENRE = "Horror";
    private static final String DEFAULT_AUTHOR = "Juan Osorio";
    private static final String DEFAULT_PUBLISHER = "Publisher";
    private static final String DEFAULT_YEAR = "2020";

    private Book bookToSave;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUpData() {
        bookToSave = new Book();
        bookToSave.setGenre(HORROR_GENRE);
        bookToSave.setAuthor(DEFAULT_AUTHOR);
        bookToSave.setImage("horror.jpg");
        bookToSave.setTitle("Title");
        bookToSave.setSubtitle("Subtitle");
        bookToSave.setPublisher(DEFAULT_PUBLISHER);
        bookToSave.setYear(DEFAULT_YEAR);
        bookToSave.setPages(PAGE_NUMBER);
        bookToSave.setIsbn("0909-1234-6789-X");

        bookRepository.save(bookToSave);

    }

    @Test
    void whenFindAll_thenReturnAllBooks() {
        Iterable<Book> books = bookRepository.findAll();
        assertThat(books.iterator().hasNext()).isTrue();
        assertThat(books.iterator().next()).isEqualToComparingFieldByFieldRecursively(bookToSave);
    }

    @Test
    void whenFindByAuthor_thenReturnBook() {
        Book bookFound = bookRepository.findByAuthor(DEFAULT_AUTHOR).get();
        assertThat(bookFound).isEqualToComparingFieldByFieldRecursively(bookToSave);
    }

    @Test
    void whenUpdate_thenReturnUpdatedBook() {
        Book bookFoundOld = bookRepository.findByAuthor(DEFAULT_AUTHOR).get();
        bookFoundOld.setGenre("Comedy");

        bookRepository.save(bookFoundOld);
        Book bookFoundUpdated = bookRepository.findByAuthor(DEFAULT_AUTHOR).get();

        assertThat(bookFoundUpdated.getGenre()).isEqualTo(bookFoundOld.getGenre());
        assertThat(bookFoundUpdated.getAuthor()).isEqualTo(bookFoundOld.getAuthor());
        assertThat(bookFoundUpdated.getIsbn()).isEqualTo(bookFoundOld.getIsbn());
    }

    @Test
    void whenDelete_thenReturnZeroBooks() {
        Book bookFound = bookRepository.findByAuthor(DEFAULT_AUTHOR).get();
        bookRepository.delete(bookFound);
        Iterable<Book> books = bookRepository.findAll();
        assertThat(books.iterator().hasNext()).isFalse();
    }

}
