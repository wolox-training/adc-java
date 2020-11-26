package wolox.training.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wolox.training.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Method that obtain a book by its author
     *
     * @param author: Name of author (String)
     * @return {@link Book}
     */
    Optional<Book> findByAuthor(String author);

    /**
     * Method that obtain a book by its isbn
     *
     * @param isbn: Isbn of book (String)
     * @return {@link Book}
     */
    Optional<Book> findByIsbn(String isbn);

}
