package com.exercise.bookcatalogue.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.exercise.bookcatalogue.exception.ValidationError;
import com.exercise.bookcatalogue.model.entity.Book;
import com.exercise.bookcatalogue.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {BookController.class})
@ExtendWith(SpringExtension.class)
class BookControllerTest {
    @Autowired
    private BookController bookController;

    @MockBean
    private BookService bookService;

    @MockBean
    private KafkaTemplate<String, String> kafkaTemplate;

    @MockBean
    private ValidationError validationError;

    @Test
    void testAddBook() throws Exception {
        Book book = new Book();
        book.setAuthors(new HashSet<>());
        book.setIsbn("Isbn");
        book.setId(123L);
        book.setTitle("Dr");
        book.setPublicationDate(null);
        String content = (new ObjectMapper()).writeValueAsString(book);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.bookController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(405));
    }

    @Test
    void testDeleteBook() throws Exception {
        doNothing().when(this.bookService).deleteBook((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/book/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.bookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testDeleteBook2() throws Exception {
        doNothing().when(this.bookService).deleteBook((Long) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/book/{id}", 123L);
        deleteResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.bookController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetListOfBooksByAuthor() throws Exception {
        when(this.bookService.getBooksByAuthor((String) any())).thenReturn(new HashSet<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/book/author/{name}", "Name");
        MockMvcBuilders.standaloneSetup(this.bookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetListOfBooksByAuthor2() throws Exception {
        Book book = new Book();
        book.setAuthors(new HashSet<>());
        book.setIsbn("?");
        book.setId(123L);
        book.setTitle("Dr");
        book.setPublicationDate(LocalDate.ofEpochDay(1L));

        HashSet<Book> bookSet = new HashSet<>();
        bookSet.add(book);
        when(this.bookService.getBooksByAuthor((String) any())).thenReturn(bookSet);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/book/author/{name}", "Name");
        MockMvcBuilders.standaloneSetup(this.bookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("[{\"id\":123,\"title\":\"Dr\",\"isbn\":\"?\",\"publicationDate\":[1970,1,2],\"authors\":[]}]"));
    }

    @Test
    void testGetListOfBooksByIsbn() throws Exception {
        when(this.bookService.getBooksByIsbn((String) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/book/isbn/{isbn}", "Isbn");
        MockMvcBuilders.standaloneSetup(this.bookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetListOfBooksByIsbn2() throws Exception {
        Book book = new Book();
        book.setAuthors(new HashSet<>());
        book.setIsbn("?");
        book.setId(123L);
        book.setTitle("Dr");
        book.setPublicationDate(LocalDate.ofEpochDay(1L));

        ArrayList<Book> bookList = new ArrayList<>();
        bookList.add(book);
        when(this.bookService.getBooksByIsbn((String) any())).thenReturn(bookList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/book/isbn/{isbn}", "Isbn");
        MockMvcBuilders.standaloneSetup(this.bookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("[{\"id\":123,\"title\":\"Dr\",\"isbn\":\"?\",\"publicationDate\":[1970,1,2],\"authors\":[]}]"));
    }

    @Test
    void testGetListOfBooksByTitle() throws Exception {
        when(this.bookService.getBooksByTitle((String) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/book/title/{title}", "Dr");
        MockMvcBuilders.standaloneSetup(this.bookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetListOfBooksByTitle2() throws Exception {
        Book book = new Book();
        book.setAuthors(new HashSet<>());
        book.setIsbn("?");
        book.setId(123L);
        book.setTitle("Dr");
        book.setPublicationDate(LocalDate.ofEpochDay(1L));

        ArrayList<Book> bookList = new ArrayList<>();
        bookList.add(book);
        when(this.bookService.getBooksByTitle((String) any())).thenReturn(bookList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/book/title/{title}", "Dr");
        MockMvcBuilders.standaloneSetup(this.bookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("[{\"id\":123,\"title\":\"Dr\",\"isbn\":\"?\",\"publicationDate\":[1970,1,2],\"authors\":[]}]"));
    }

    @Test
    void testUpdateBook() throws Exception {
        doNothing().when(this.validationError)
                .inputValidationErrors((org.springframework.validation.BindingResult) any());

        Book book = new Book();
        book.setAuthors(new HashSet<>());
        book.setIsbn("Isbn");
        book.setId(123L);
        book.setTitle("Dr");
        book.setPublicationDate(null);
        String content = (new ObjectMapper()).writeValueAsString(book);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/book/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.bookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

