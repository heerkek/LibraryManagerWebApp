package com.hasanemreerkek.controller;


import com.hasanemreerkek.dao.BookDAO;
import com.hasanemreerkek.model.Book;
import com.hasanemreerkek.services.BookService;
import com.hasanemreerkek.services.CaptcheService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import static org.mockito.Mockito.*;

/**
 * Created by Emre on 18.10.2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {
    @InjectMocks
    private BookController bookController;

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @Mock
    private CaptcheService captcheService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();

    }
    @Test
    public void testGetBook() throws Exception {
        Book book = new Book();
        book.setId("1");
        book.setName("book1");
        book.setAuthor("author1");

        when(bookService.getBook("1")).thenReturn(book);

        mockMvc.perform(get("/book/1"))
                .andExpect(jsonPath("$.name", is(book.getName())))
                .andExpect(jsonPath("$.author", is(book.getAuthor())))
                .andExpect(status().isOk());

    }
    @Test
    public void testAddBook() throws Exception {
        Book book = new Book();
        book.setId("1");
        book.setName("book1");
        book.setAuthor("author1");

        when(bookService.addBook(any(Book.class))).thenReturn(book);
        when(captcheService.isSuccess(anyString())).thenReturn(true);

        mockMvc.perform(post("/book/?grecaptchaResponse=dqwrqwrr14afsfaw")
                .content("{\"id\":\"1\",\"name\":\"book1\",\"author\":\"author1\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateBook() throws Exception {
        Book updatedBook = new Book();
        updatedBook.setId("1");
        updatedBook.setName("book2");
        updatedBook.setAuthor("author2");

        when(bookService.updateBook(any(Book.class)))
                .thenReturn(updatedBook);

        mockMvc.perform(put("/book/")
                .content("{\"id\":\"1\",\"name\":\"book2\",\"author\":\"author2\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(updatedBook.getName())))
                .andExpect(jsonPath("$.author", is(updatedBook.getAuthor())))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateInValidBook() throws Exception {

        /*
        when(bookService.updateBook(any(Book.class)))
                .thenReturn(null);

        mockMvc.perform(put("/book/")
                .content("{\"id\":\"1\",\"name\":null,\"author\":\"author2\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isExpectationFailed());

                */
    }

    @Test
    public void testDeleteBook() throws Exception {
        Book deletedBook = new Book();
        deletedBook.setId("1");
        deletedBook.setName("book1");
        deletedBook.setAuthor("author1");

        when(bookService.deleteBook("1")).thenReturn(deletedBook);

        mockMvc.perform(delete("/book/1"))
                .andExpect(jsonPath("$.name", is(deletedBook.getName())))
                .andExpect(jsonPath("$.author", is(deletedBook.getAuthor())))
                .andExpect(status().isOk());
    }
}
