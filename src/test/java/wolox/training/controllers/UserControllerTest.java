package wolox.training.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;
import wolox.training.security.AuthenticationProvider;
import wolox.training.services.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    private static final String USER_PATH = "/api/users";
    private static final int USER_ID = 0;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private AuthenticationProvider authenticationProvider;
    @MockBean
    private PasswordEncoder passwordEncoder;
    private ObjectMapper objectMapper;
    private User userTest;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        userTest = new User();
        userTest.setUsername("test.username");
        userTest.setName("Test");
        userTest.setBirthdate(LocalDate.now().minusYears(1));
    }

    @Test
    @WithMockUser
    void whenFindAll_thenReturnAllUsers() throws Exception {
        when(userService.findAll()).thenReturn(Collections.singleton(userTest));
        mockMvc.perform(get(USER_PATH)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("[0].username").value(userTest.getUsername()))
                .andExpect(jsonPath("[0].name").value(userTest.getName()));
    }

    @Test
    @WithMockUser
    void whenFindById_thenReturnUser() throws Exception {
        when(userService.findById(any())).thenReturn(userTest);
        mockMvc.perform(get(USER_PATH + "/{id}", USER_ID)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("username").value(userTest.getUsername()))
                .andExpect(jsonPath("name").value(userTest.getName()));
    }

    @Test
    @WithMockUser
    void whenDelete_thenReturnIsOk() throws Exception {
        when(userService.findById(any())).thenReturn(userTest);
        doNothing().when(userService).delete(any());
        mockMvc.perform(delete(USER_PATH + "/{id}", USER_ID)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print()).andExpect(status().isOk());
    }

}