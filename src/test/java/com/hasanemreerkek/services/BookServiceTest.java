package com.hasanemreerkek.services;

import com.hasanemreerkek.controller.BookController;
import com.hasanemreerkek.dao.BookDAO;
import com.hasanemreerkek.model.Book;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by Emre on 18.10.2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {
    @InjectMocks
    private BookService bookService;

    private MockMvc mockMvc;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private BookDAO bookDAO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookService).build();
    }

    @Test
    @Transactional
    public void testGetBook() throws Exception {
        Book book = new Book();
        book.setId("1");
        book.setName("book1");
        book.setAuthor("author1");

        when(bookDAO.getBook("1")).thenReturn(book);

        Assert.assertEquals(bookService.getBook("1"), book);
    }

    @Test
    @Transactional
    public void testAddBook() throws Exception {
        Book book = new Book();
        book.setId("1");
        book.setName("book1");
        book.setAuthor("author1");

        when(bookDAO.addBook(book)).thenReturn(book);

        Assert.assertEquals(bookService.addBook(book), book);
    }

    @Test
    @Transactional
    public void testAddInValidBook() throws Exception {
        Book book1 = new Book();
        book1.setId("1");
        book1.setName(null);
        book1.setAuthor("author1");

        Book book2 = new Book();
        book2.setId("2");
        book2.setName("book2");
        book2.setAuthor("");

        try{
            bookService.addBook(book1);
        }catch (IllegalArgumentException e){
            Assert.assertEquals("{'message' : 'The name of book cannot be empty.'}", e.getMessage());
        }
        try{
            bookService.addBook(book2);
        }catch (IllegalArgumentException e){
            Assert.assertEquals("{'message' : 'The author cannot be empty.'}", e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testUpdateBook() throws Exception {
        Book book = new Book();
        book.setId("1");
        book.setName("book1");
        book.setAuthor("author1");

        when(bookDAO.updateBook(any(Book.class))).thenReturn(book);

        Assert.assertEquals(bookService.updateBook(book), book);
    }

    @Test
    @Transactional
    public void testUpdateInValidBook() throws Exception {
        Book book1 = new Book();
        book1.setId("1");
        book1.setName(null);
        book1.setAuthor("author1");

        Book book2 = new Book();
        book2.setId("2");
        book2.setName("book2");
        book2.setAuthor("");

        try{
            bookService.updateBook(book1);
        }catch (IllegalArgumentException e){
            Assert.assertEquals("{'message' : 'The name of book cannot be empty.'}", e.getMessage());
        }
        try{
            bookService.updateBook(book2);
        }catch (IllegalArgumentException e){
            Assert.assertEquals("{'message' : 'The author cannot be empty.'}", e.getMessage());
        }
    }


    @Test
    @Transactional
    public void testDeleteBook() throws Exception {
        Book book = new Book();
        book.setId("1");
        book.setName("book1");
        book.setAuthor("author1");

        when(bookDAO.isExistBook("1")).thenReturn(true);
        when(bookDAO.deleteBook("1")).thenReturn(book);

        Assert.assertEquals(bookService.deleteBook("1"), book);
    }

    @Test
    @Transactional
    public void testDeleteNotExistBook() throws Exception {
        Book book = new Book();
        book.setId("1");
        book.setName("book1");
        book.setAuthor("author1");

        when(bookDAO.isExistBook("1")).thenReturn(false);

        try{
            bookService.deleteBook("1");
        }catch (IllegalArgumentException e){
            Assert.assertEquals("{'message' : 'The book is not available, cannot be deleted.'}", e.getMessage());
        }
    }


    @Test
    @Transactional
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


        when(bookDAO.getBooks(0, 20)).thenReturn(list);

        Assert.assertEquals(bookService.getBooks(0, 20), list);
    }
}
