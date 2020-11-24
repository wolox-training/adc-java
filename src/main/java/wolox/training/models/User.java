package wolox.training.models;

import com.sun.istack.NotNull;
import wolox.training.exceptions.BookAlreadyOwnedException;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Table(name = "users")
@Entity
public class User {

    public User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotNull
    @Column(nullable = false)
    private String username;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private LocalDate birthdate;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private List<Book> books = Collections.emptyList();

    public List<Book> getBooks() {
        return (List<Book>) Collections.unmodifiableList(books);
    }

    public void setBook(Book book) throws BookAlreadyOwnedException {
        if (this.books.contains(book)) throw new BookAlreadyOwnedException();
        this.books.add(book);
    }
    
    public boolean removeBook(Book book) {
        return books.remove(book);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        checkNotNull(username, "Please check username field, its null");
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        checkNotNull(name, "Please check name field, its null");
        this.name = name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        checkNotNull(birthdate, "Please check birthdate field, its null");
        checkArgument(birthdate.isBefore(LocalDate.now()),
                "Please check birthdate field, its can't be today or greater than today");
        this.birthdate = birthdate;
    }
}
