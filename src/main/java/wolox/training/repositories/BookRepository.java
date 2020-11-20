package wolox.training.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import wolox.training.models.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Method that obtain a book by its author
     *
     * @param author: Name of author (String)
     * @return {@link Book}
     */
    Optional<Book> findByAuthor(String author);

}
