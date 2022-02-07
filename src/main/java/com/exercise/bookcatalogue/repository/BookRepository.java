package com.exercise.bookcatalogue.repository;

import com.exercise.bookcatalogue.model.entity.Author;
import com.exercise.bookcatalogue.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b where b.id = ?1")
    Optional<Book> findById(Long id);

    @Query("select b from Book b where b.title = ?1")
    Optional<List<Book>> findByTitle(String title);

    @Query("select b from Book b where b.isbn = ?1")
    Optional<List<Book>> findByIsbn(String isbn);
}
