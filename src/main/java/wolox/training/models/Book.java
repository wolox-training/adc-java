package wolox.training.models;

import com.sun.istack.NotNull;
import wolox.training.exceptions.BookAlreadyOwnedException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Collections;
import java.util.List;

@Entity
public class Book {

    public Book() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @NotNull
    @Column(nullable = false)
    private String genre;
    @NotNull
    @Column(nullable = false)
    private String author;
    @NotNull
    @Column(nullable = false)
    private String image;
    @NotNull
    @Column(nullable = false)
    private String title;
    @NotNull
    @Column(nullable = false)
    private String subtitle;
    @NotNull
    @Column(nullable = false)
    private String publisher;
    @NotNull
    @Column(nullable = false)
    private String year;
    @NotNull
    @Column(nullable = false)
    private int pages;
    @NotNull
    @Column(nullable = false, unique = true)
    private String isbn;
    @ManyToMany(mappedBy = "books")
    private List<User> users = Collections.emptyList();

    public List<User> getUsers() {
        return (List<User>) Collections.unmodifiableList(users);
    }

    public void setUsers(User user) throws BookAlreadyOwnedException {
        if (this.users.contains(user)) throw new BookAlreadyOwnedException();
        this.users.add(user);
    }

    public boolean removeUser(User user) {
        return users.remove(user);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        subtitle = subtitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        publisher = publisher;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        year = year;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        pages = pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
