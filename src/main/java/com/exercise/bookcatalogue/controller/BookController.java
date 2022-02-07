package com.exercise.bookcatalogue.controller;

import com.exercise.bookcatalogue.exception.ValidationError;
import com.exercise.bookcatalogue.model.entity.Book;
import com.exercise.bookcatalogue.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("book")
public class BookController {

    private final BookService     bookService;
    private final ValidationError validationError;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public BookController(BookService bookService, ValidationError validationError,
                          KafkaTemplate<String, String> kafkaTemplate) {
        this.bookService = bookService;
        this.validationError = validationError;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping()
    public ResponseEntity<String> addBook(@Valid @RequestBody Book book, BindingResult errors) {

        ResponseEntity<String> response = null;
        if (errors.hasErrors()) {
            validationError.inputValidationErrors(errors);
        } else {
            final long bookId = bookService.addBook(book);
            response = new ResponseEntity<String>(
                    String.format("New book, with id #%d has been added to the catalogue.",
                            bookId), HttpStatus.OK);
            kafkaTemplate.send("bookCatalogue", String.format("New book, with id #%d has been added to the catalogue.",
                    bookId));
        }
        return response;
    }


    @PutMapping("{id}")
    public ResponseEntity<HttpStatus> updateBook(@PathVariable("id") Long id, @Valid @RequestBody Book updatesToBook,
                                                 BindingResult errors) {

        ResponseEntity<HttpStatus> response = null;
        if (errors.hasErrors()) {
            validationError.inputValidationErrors(errors);
        } else {
            bookService.updateBook(id, updatesToBook);
            response = new ResponseEntity<>(HttpStatus.OK);
        }
        return response;
    }

    @GetMapping("title/{title}")
    public ResponseEntity<List<Book>> getListOfBooksByTitle(@PathVariable("title") String title) {
        List<Book> booksByTitle = bookService.getBooksByTitle(title);
        return new ResponseEntity<>(booksByTitle, HttpStatus.OK);
    }

    @GetMapping("author/{name}")
    public ResponseEntity<Set<Book>> getListOfBooksByAuthor(@PathVariable("name") String name) {
        Set<Book> booksByAuthor = bookService.getBooksByAuthor(name);
        return new ResponseEntity<>(booksByAuthor, HttpStatus.OK);
    }

    @GetMapping("isbn/{isbn}")
    public ResponseEntity<List<Book>> getListOfBooksByIsbn(@PathVariable("isbn") String isbn) {
        List<Book> booksByIsbn = bookService.getBooksByIsbn(isbn);
        return new ResponseEntity<>(booksByIsbn, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
