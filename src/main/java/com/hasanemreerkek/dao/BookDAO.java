package com.hasanemreerkek.dao;

import com.hasanemreerkek.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Emre on 15.10.2015.
 *
 * DB Operations
 */
@Repository
public class BookDAO {
    @Qualifier("mongoTemplate")
    @Autowired
    private MongoOperations mongoOperation;

    /**
     * get the Book with the id from DB
     * @param id the id of the Book
     * @return the Book or null if the Book with cannot be found
     */
    public Book getBook(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoOperation.findOne(query, Book.class);
    }

    /**
     * get list of the book from DB
     * @param start first index of The Book DAO
     * @param limit last index of The Book DAO
     * @return list of the Book
     */
    public List<Book> getBooks(int start, int limit) {
        Query query = new Query();
        query.skip(start);
        query.limit(limit);
        return mongoOperation.find(query, Book.class);
    }

    /**
     * add the Book to DB
     * @param book the Book
     * @return added the Book
     */
    public Book addBook(Book book) {
        mongoOperation.save(book);
        return book;
    }

    /**
     * update the Book from DB
     * @param book the Book
     * @return updated the Book
     */
    public Book updateBook(Book book) {
        mongoOperation.save(book);
        return book;
    }

    /**
     * delete the Book from DB
     * @param id the id of the Book
     * @return deleted the Book
     */
    public Book deleteBook(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoOperation.findAndRemove(query, Book.class);
    }

    /**
     * status of The Book is exist
     * @param id the id of the Book
     * @return true if the the Book is exist, false if the the Book is not exist
     */
    public boolean isExistBook(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Book existBook = mongoOperation.findOne(query, Book.class);
        return (existBook != null);
    }

}
