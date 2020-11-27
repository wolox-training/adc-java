package wolox.training.client.delegate;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wolox.training.client.feign.OpenLibraryFeignClient;
import wolox.training.client.models.BookInfoDto;
import wolox.training.client.models.BooksParam;

@Component
public class OpenLibraryDelegate {

    private static final String ISBN_PARAM = "ISBN:";

    @Autowired
    private OpenLibraryFeignClient openLibraryFeignClient;

    @Autowired
    private ObjectMapper objectMapper;

    public Optional<BookInfoDto> findBookByIsbn(String isbn) {
        StringBuilder isbnParam = new StringBuilder();
        isbnParam.append(ISBN_PARAM);
        isbnParam.append(isbn);

        HashMap<String, Object> bookInfoResponse = openLibraryFeignClient
                .findBookByIsbn(new BooksParam(isbnParam.toString()));

        return Optional.ofNullable(bookInfoResponse.get(isbnParam.toString()))
                .map(bookInfo -> objectMapper.convertValue(bookInfo, BookInfoDto.class));
    }
}
