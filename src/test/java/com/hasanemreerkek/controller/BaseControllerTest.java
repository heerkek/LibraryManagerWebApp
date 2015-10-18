package com.hasanemreerkek.controller;

import com.hasanemreerkek.model.Book;
import com.hasanemreerkek.services.BookService;
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
import static org.hamcrest.Matchers.*;


import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emre on 18.10.2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class BaseControllerTest {
    @InjectMocks
    private BaseController baseController;

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(baseController).build();
    }

    @Test
    public void testGetBooks() throws Exception {
        List<Book> list = new ArrayList<Book>();

        Book book1 = new Book();
        book1.setId("1");
        book1.setName("book1");
        book1.setAuthor("author1");
        list.add(book1);

        Book book2 = new Book();
        book2.setId("2");
        book2.setName("book2");
        book2.setAuthor("author2");
        list.add(book2);


        when(bookService.getBooks(0, 20)).thenReturn(list);

        mockMvc.perform(get("/1"))
                .andExpect(jsonPath("$[*]",hasSize(2)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("book1", "book2")))
                .andExpect(jsonPath("$[*].author", containsInAnyOrder("author1", "author2")))
                .andExpect(status().isOk());
    }
}
