package com.hasanemreerkek.controller;

import com.hasanemreerkek.config.SpringMongoConfig;
import com.hasanemreerkek.model.Book;
import com.hasanemreerkek.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Base Controller
 */
@Controller
@RequestMapping("/")
public class BaseController {

    @Autowired
    BookService bookService;

    /**
     * welcome page
     * @return the modelAndView
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    /**
     * get list of the book
     * @param page the number of the page
     * @return list of the book is limited by page and limit count
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{page}", produces={"application/json"}, method = RequestMethod.GET)
    public List<Book> getBooks(@PathVariable("page") int page) {
        final int limit = 20;
        final int start = (page == 1) ? 0 : page * limit - limit;
        return bookService.getBooks(start, limit);
    }


}