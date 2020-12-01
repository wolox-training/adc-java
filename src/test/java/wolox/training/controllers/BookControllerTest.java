package wolox.training.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.security.AuthenticationProvider;
import wolox.training.services.BookService;

@WebMvcTest(BookController.class)
class BookControllerTest {

    private static final String BOOK_PATH = "/api/books";
    private static final int BOOK_ID = 0;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;
    @MockBean
    private AuthenticationProvider authenticationProvider;
    @MockBean
    private PasswordEncoder passwordEncoder;
    private ObjectMapper objectMapper;
    private Book bookTest;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
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
    @WithMockUser
    void whenFindAll_thenReturnAllBooks() throws Exception {
        when(bookService.findAll()).thenReturn(Collections.singleton(bookTest));
        mockMvc.perform(get(BOOK_PATH)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.singleton(bookTest))))
                .andExpect(jsonPath("[0].genre").value(bookTest.getGenre()))
                .andExpect(jsonPath("[0].author").value(bookTest.getAuthor()))
                .andExpect(jsonPath("[0].title").value(bookTest.getTitle()))
                .andExpect(jsonPath("[0].subtitle").value(bookTest.getSubtitle()));
    }

    @Test
    @WithMockUser
    void whenFindById_thenReturnBook() throws Exception {
        when(bookService.findById(any())).thenReturn(bookTest);
        mockMvc.perform(get(BOOK_PATH + "/{id}", BOOK_ID)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(bookTest)))
                .andExpect(jsonPath("publisher").value(bookTest.getPublisher()))
                .andExpect(jsonPath("year").value(bookTest.getYear()))
                .andExpect(jsonPath("pages").value(bookTest.getPages()))
                .andExpect(jsonPath("isbn").value(bookTest.getIsbn()));
    }

    @Test
    @WithMockUser
    void whenFindById_thenReturnNotFound() throws Exception {
        when(bookService.findById(any())).thenThrow(BookNotFoundException.class);
        mockMvc.perform(get(BOOK_PATH + "/{id}", BOOK_ID)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void whenSave_thenReturnSavedBook() throws Exception {
        when(bookService.save(any())).thenReturn(bookTest);
        mockMvc.perform(post(BOOK_PATH)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(bookTest)))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(bookTest)))
                .andExpect(jsonPath("genre").value(bookTest.getGenre()))
                .andExpect(jsonPath("author").value(bookTest.getAuthor()))
                .andExpect(jsonPath("title").value(bookTest.getTitle()))
                .andExpect(jsonPath("subtitle").value(bookTest.getSubtitle()));
    }

    @Test
    @WithMockUser
    void whenUpdate_thenReturnUpdatedBook() throws Exception {
        when(bookService.update(any(), any())).thenReturn(bookTest);
        mockMvc.perform(put(BOOK_PATH + "/{id}", BOOK_ID)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(bookTest)))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(bookTest)))
                .andExpect(jsonPath("genre").value(bookTest.getGenre()))
                .andExpect(jsonPath("author").value(bookTest.getAuthor()))
                .andExpect(jsonPath("title").value(bookTest.getTitle()))
                .andExpect(jsonPath("subtitle").value(bookTest.getSubtitle()));
    }

    @Test
    @WithMockUser
    void whenUpdate_thenReturnNotFound() throws Exception {
        when(bookService.update(any(), any())).thenThrow(BookNotFoundException.class);
        mockMvc.perform(put(BOOK_PATH + "/{id}", BOOK_ID)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(bookTest)))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void whenDelete_thenReturnIsOk() throws Exception {
        doNothing().when(bookService).delete(any());
        mockMvc.perform(delete(BOOK_PATH + "/{id}", BOOK_ID)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void whenDelete_thenReturnNotFound() throws Exception {
        doThrow(BookNotFoundException.class).when(bookService).delete(any());
        mockMvc.perform(delete(BOOK_PATH + "/{id}", BOOK_ID)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print()).andExpect(status().isNotFound());
    }

}
