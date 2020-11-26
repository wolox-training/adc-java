
package wolox.training.client.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BookInfoDto {

    private String isbn;
    private String title;
    private String publishDate;
    private int numberOfPages;
    private List<PublisherDto> publishers;
    private CoverDto cover;
    private List<AuthorDto> authors;

    public BookInfoDto() {
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public List<PublisherDto> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<PublisherDto> publishers) {
        this.publishers = publishers;
    }

    public CoverDto getCover() {
        return cover;
    }

    public void setCover(CoverDto cover) {
        this.cover = cover;
    }

    public List<AuthorDto> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorDto> authors) {
        this.authors = authors;
    }
}
