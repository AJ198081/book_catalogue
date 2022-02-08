package com.exercise.bookcatalogue.service;

import com.exercise.bookcatalogue.exception.ExerciseCustomException;
import com.exercise.bookcatalogue.model.entity.Author;
import com.exercise.bookcatalogue.model.entity.Book;
import com.exercise.bookcatalogue.repository.AuthorRepository;
import com.exercise.bookcatalogue.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BookService {

    private final BookRepository                bookRepository;
    private final AuthorRepository              authorRepository;
    private       KafkaTemplate<String, String> kafkaTemplate;


    @Autowired
    public BookService(BookRepository bookRepository,
                       AuthorRepository authorRepository,
                       KafkaTemplate<String, String> kafkaTemplate) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public long addBook(Book bookDto) {

        final Book book = Book.builder()
                .isbn(bookDto.getIsbn())
                .authors(new HashSet<>())
                .publicationDate(bookDto.getPublicationDate())
                .title(bookDto.getTitle())
                .build();

        final Set<Author> authors = bookDto.getAuthors();
        for (Author author : authors) {
            final Optional<Author> authorByName = authorRepository.findByName(author.getName());
            if (authorByName.isPresent()) {
                book.addAuthor(authorByName.get());
            } else {
                book.addAuthor(author);
            }
        }

        bookRepository.save(book);

        kafkaTemplate.send("bookCatalogue",
                String.format("New bookDto {%s} has been added to Book Catalogue.", book));

        return book.getId();
    }

    public void updateBook(Long id, Book bookDto) {
        final Optional<Book> bookById = bookRepository.findById(id);

        if (bookById.isPresent()) {
            final Book book = Book.builder()
                    .id(id)
                    .authors(new HashSet<>())
                    .isbn(bookDto.getIsbn())
                    .publicationDate(bookDto.getPublicationDate())
                    .title(bookDto.getTitle())
                    .build();

            if (bookDto.getAuthors().isEmpty()) {
                book.setAuthors(bookById.get().getAuthors());
            } else {
                final Set<Author> authors = bookDto.getAuthors();
                for (Author author : authors) {
                    final Optional<Author> authorByName = authorRepository.findByName(author.getName());
                    if (authorByName.isPresent()) {
                        book.addAuthor(authorByName.get());
                    } else {
                        book.addAuthor(author);
                    }
                }
            }
            bookRepository.save(book);

            kafkaTemplate.send("bookCatalogue",
                    String.format("Following changes have been made to {%s} in the Book Catalogue.",
                            book.toString()));

        } else {
            throw new ExerciseCustomException(
                    String.format("Unable to find book with Id %d, please check the path parameter" +
                            "try again.", id));
        }
    }

    public List<Book> getBooksByTitle(String title) {
        List<Book> bookList = null;

        final Optional<List<Book>> listBooksByTitle = bookRepository.findByTitle(title);

        if (listBooksByTitle.isPresent()) {
            if (!listBooksByTitle.get().isEmpty()) {
                bookList = listBooksByTitle.get();
            } else {
                throw new ExerciseCustomException("Unable to find any book for title " + title);
            }
        }
        return bookList;
    }

    public List<Book> getBooksByIsbn(String isbn) {
        List<Book> bookList = null;

        final Optional<List<Book>> listBooksByIsbn = bookRepository.findByIsbn(isbn);

        if (listBooksByIsbn.isPresent()) {
            if (!listBooksByIsbn.get().isEmpty()) {
                bookList = listBooksByIsbn.get();
            } else {
                throw new ExerciseCustomException("Unable to find any book for ISBN " + isbn);
            }
        }
        return bookList;
    }

    public Set<Book> getBooksByAuthor(String authorName) {
        Set<Book> books = null;
        final Optional<Author> author = authorRepository.findByName(authorName);
        if (author.isPresent()) {
            books = author.get().getBooks();
            if (books.isEmpty()) {
                throw new ExerciseCustomException("Unable to find any book for author " + authorName);
            }
        } else {
            throw new ExerciseCustomException(String.format("Author %s doesn't exist in database.", authorName));
        }
        return books;
    }

    public void deleteBook(Long id) {
        final Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            final Book book1 = book.get();
            book1.setAuthors(null);
            bookRepository.delete(book1);
            kafkaTemplate.send("bookCatalogue",
                    String.format("Book {%s} has been deleted from the Catalogue.", book1.toString()));
        } else {
            throw new ExerciseCustomException(String.format("No book with Id %d exists in Catalogue.", id
            ));
        }
    }
}
