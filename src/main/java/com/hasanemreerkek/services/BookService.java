package com.hasanemreerkek.services;

import com.hasanemreerkek.dao.BookDAO;
import com.hasanemreerkek.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by Emre on 15.10.2015.
 *
 * BookService
 */
@Service
public class BookService {
    @Autowired
    private BookDAO bookDAO;

    /**
     * get the book with book id
     * @param id the id of the book
     * @return the Book or null if the Book is not exist form DB
     */
    @Transactional(readOnly = true)
    public Book getBook(String id){
        return bookDAO.getBook(id);
    }

    /**
     * add the Book if the Book is valid and is not exist
     * @param book the Book
     * @return added the Book
     */
    @Transactional
    public Book addBook(Book book){
        if (!StringUtils.hasText(book.getName()))
            throw new IllegalArgumentException("{\"message\" : \"The name of book cannot be empty.\"}");

        if (!StringUtils.hasText(book.getAuthor()))
            throw new IllegalArgumentException("{\"message\" : \"The author cannot be empty.\"}");

        if (bookDAO.isExistBook(book.getId()))
            throw new IllegalArgumentException("{\"message\" : \"The book is available, cannot be added.\"}");

        return bookDAO.addBook(book);
    }

    /**
     * update the Book if the Book is valid
     * @param book the Book
     * @return updated the Book or null if the Book with cannot be found
     */
    @Transactional
    public Book updateBook(Book book){
        if (!StringUtils.hasText(book.getName()))
            throw new IllegalArgumentException("{\"message\" : \"The name of book cannot be empty.\"}");

        if (!StringUtils.hasText(book.getAuthor()))
            throw new IllegalArgumentException("{\"message' : \"The author cannot be empty.\"}");

        return bookDAO.updateBook(book);
    }

    /**
     * delete the Book
     * @param id the id of the Book
     * @return deleted the Book if the Book is not exist
     */
    @Transactional
    public Book deleteBook(String id){
        if (!bookDAO.isExistBook(id))
            throw new IllegalArgumentException("{\"message\" : \"The book is not available, cannot be deleted.\"}");

        return bookDAO.deleteBook(id);
    }

    /**
     * get list of the Book
     * @param start first index of The Book list
     * @param limit last index of The Book list
     * @return list of the Book
     */
    @Transactional(readOnly = true)
    public List<Book> getBooks(int start, int limit) {
        return bookDAO.getBooks(start, limit);
    }
}
