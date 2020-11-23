package wolox.training.models;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@ApiModel(description = "Training API book")
public class Book {

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
