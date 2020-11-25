package wolox.training.models;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import wolox.training.exceptions.BookAlreadyOwnedException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Entity
@ApiModel(description = "Training API book")
public class Book {

    private static final int MINIMUM_AMOUNT_OF_PAGES = 20;

    public Book() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(example = "Horror")
    private String genre;

    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(required = true, notes = "Author of the book", example = "Alex Cudriz")
    private String author;

    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(required = true, notes = "Image of the book", example = "https:comedy.jpg")
    private String image;

    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(required = true, notes = "Title of the book", example = "It is better not to breathe")
    private String title;

    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(required = true, notes = "Subtitle of the book", example = "The night is just the beginning.")
    private String subtitle;

    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(required = true, notes = "Publisher of the book", example = "Norma")
    private String publisher;

    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(required = true, notes = "Year of the book", example = "2020")
    private String year;

    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(required = true, notes = "Page number of the book", example = "300")
    private int pages;

    @NotNull
    @Column(nullable = false, unique = true)
    @ApiModelProperty(required = true, notes = "Isbn of the book", example = "8993-3232-7628-X")
    private String isbn;

    @ManyToMany(mappedBy = "books")
    private List<User> users = Collections.emptyList();

    public List<User> getUsers() {
        return (List<User>) Collections.unmodifiableList(users);
    }

    public void addUser(List<User> users) {
        this.users = users;
    }

    public void addUser(User user) throws BookAlreadyOwnedException {
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
        checkNotNull(genre, "Please check genre field, its null");
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        checkNotNull(author, "Please check author field, its null");
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        checkNotNull(image, "Please check image field, its null");
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        checkNotNull(title, "Please check title field, its null");
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        checkNotNull(subtitle, "Please check subtitle field, its null");
        this.subtitle = subtitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        checkNotNull(publisher, "Please check publisher field, its null");
        this.publisher = publisher;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        checkNotNull(year, "Please check year field, its null");
        this.year = year;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        checkArgument(pages > MINIMUM_AMOUNT_OF_PAGES,
                "Please check pages field, its can't be 20 or less than 20");
        this.pages = pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        checkNotNull(isbn, "Please check isbn field, its null");
        this.isbn = isbn;
    }
}
