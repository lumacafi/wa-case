package com.example.demo.wa1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    private UserRepository userRepositoryMock;


    User usr1 = new User(11, "Alice", "12A", LocalDateTime.now(), LocalDateTime.now());
    User usr2 = new User(22, "Bob", "22A", LocalDateTime.now(), LocalDateTime.now());
    User usr3 = new User(33, "Charlie", "32A", LocalDateTime.now(), LocalDateTime.now());


    @Test
    public void getUserById_success() throws Exception {
        Mockito.when(userRepositoryMock.findById(usr1.getId())).thenReturn(Optional.of(usr1));
        mockMvc.perform(MockMvcRequestBuilders.get("/users/" + usr1.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Alice")));
    }

    @Test
    public void createUser_success() throws Exception {
        Mockito.when(userRepositoryMock.save(usr2)).thenReturn(usr2);
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(usr2))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateUser_sucess() throws Exception {
        User updatedUser = new User(33, "Charles", "32A", LocalDateTime.now(), LocalDateTime.now());

        Mockito.when(userRepositoryMock.save(usr3)).thenReturn(usr3);
        Mockito.when(userRepositoryMock.findById(usr3.getId())).thenReturn(Optional.of(usr3));
        Mockito.when(userRepositoryMock.save(updatedUser)).thenReturn(updatedUser);


        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(usr3))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());


        mockMvc.perform(MockMvcRequestBuilders.put("/users/33")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updatedUser))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Charles")));

    }
}