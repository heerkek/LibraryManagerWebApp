package com.hasanemreerkek.controller;

import com.hasanemreerkek.model.Book;
import com.hasanemreerkek.services.BookService;
import com.hasanemreerkek.services.CaptcheService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by Emre on 13.10.2015.
 *
 * Book Controller
 */
@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    CaptcheService captcheService;

    /**
     * get the book with book id
     * @param id the id of the book
     * @return the Book or null if the Book with cannot be found
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/{id}", produces = {"application/json"}, method = RequestMethod.GET)
    public Book getBook(@PathVariable("id") String id) {
        return bookService.getBook(id);
    }

    /**
     * add the book with using BookService
     * @param book the Book
     * @param grecaptchaResponse the responce of Google ReCaptche
     * @return status of added the Book is added or is not added
     * @throws IOException
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/", produces = {"application/json"}, headers = {"content-type=application/json"}, method = RequestMethod.POST)
    public String addBook(@RequestBody final Book book, @RequestParam("grecaptchaResponse") String grecaptchaResponse) throws IOException {

        if (captcheService.isSuccess(grecaptchaResponse)) {
            bookService.addBook(book);
            return "{  \"success\": true}";
        }else
            return "{  \"success\": false}";

    }

    /**
     * update the Book with using BookService
     * @param book the Book
     * @return updated the Book or null if the Book with cannot be found
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/", produces = {"application/json"}, headers = {"content-type=application/json"}, method = RequestMethod.PUT)
    public Book updateBook(@RequestBody final Book book) {
        return bookService.updateBook(book);
    }

    /**
     * delete the Book with using BookService
     * @param id the id of the Book
     * @return deleted the Book or null if the Book with cannot be found
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/{id}", produces = {"application/json"}, method = RequestMethod.DELETE)
    public Book deleteBook(@PathVariable("id") String id) {
        return bookService.deleteBook(id);
    }

    /**
     * catch exceptions
     * @param exc fired the exception
     * @return message of the exception
     */
    @ResponseBody
    @RequestMapping(produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler(Exception exc) {
        return new ResponseEntity<String>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
