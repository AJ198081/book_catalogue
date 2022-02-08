package com.exercise.bookcatalogue.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

class BookTest {
    @Test
    void testAddAuthor() {
        Book book = new Book();

        Author author = new Author();
        author.setBooks(new HashSet<>());
        author.setId(123L);
        author.setName("Name");
        book.addAuthor(author);
        assertEquals(1, book.getAuthors().size());
    }

    @Test
    void testEquals() {
        Book book = new Book();
        book.setAuthors(new HashSet<>());
        book.setIsbn("1234567890123");
        book.setId(123L);
        book.setTitle("Dr");
        book.setPublicationDate(LocalDate.ofEpochDay(1L));
        assertFalse(book.equals(null));
    }

    @Test
    void testEquals2() {
        Book book = new Book();
        book.setAuthors(new HashSet<>());
        book.setIsbn("1234567890123");
        book.setId(123L);
        book.setTitle("Dr");
        book.setPublicationDate(LocalDate.ofEpochDay(1L));
        assertFalse(book.equals("Different type to Book"));
    }

    @Test
    void testEquals3() {
        Book book = new Book();
        book.setAuthors(new HashSet<>());
        book.setIsbn("1234567890123");
        book.setId(123L);
        book.setTitle("Dr");
        book.setPublicationDate(LocalDate.ofEpochDay(1L));
        assertTrue(book.equals(book));
        int expectedHashCodeResult = book.hashCode();
        assertEquals(expectedHashCodeResult, book.hashCode());
    }

    @Test
    void testEquals4() {
        Book book = new Book();
        book.setAuthors(new HashSet<>());
        book.setIsbn("1234567890123");
        book.setId(123L);
        book.setTitle("Dr");
        book.setPublicationDate(LocalDate.ofEpochDay(1L));

        Book book1 = new Book();
        book1.setAuthors(new HashSet<>());
        book1.setIsbn("1234567890123");
        book1.setId(123L);
        book1.setTitle("Dr");
        book1.setPublicationDate(LocalDate.ofEpochDay(1L));
        assertTrue(book.equals(book1));
        int expectedHashCodeResult = book.hashCode();
        assertEquals(expectedHashCodeResult, book1.hashCode());
    }

    @Test
    void testEquals5() {
        Book book = new Book();
        book.setAuthors(new HashSet<>());
        book.setIsbn("1234567890123");
        book.setId(123L);
        book.setTitle("Dr");
        book.setPublicationDate(LocalDate.ofEpochDay(1L));

        Book book1 = new Book();
        book1.setAuthors(new HashSet<>());
        book1.setIsbn("Isbn");
        book1.setId(123L);
        book1.setTitle("Dr");
        book1.setPublicationDate(LocalDate.ofEpochDay(1L));
        assertFalse(book.equals(book1));
    }

    @Test
    void testEquals6() {
        Book book = new Book();
        book.setAuthors(new HashSet<>());
        book.setIsbn("Isbn");
        book.setId(0L);
        book.setTitle("Dr");
        book.setPublicationDate(LocalDate.ofEpochDay(1L));

        Book book1 = new Book();
        book1.setAuthors(new HashSet<>());
        book1.setIsbn("Isbn");
        book1.setId(123L);
        book1.setTitle("Dr");
        book1.setPublicationDate(LocalDate.ofEpochDay(1L));
        assertFalse(book.equals(book1));
    }

    @Test
    void testEquals7() {
        Book book = new Book();
        book.setAuthors(new HashSet<>());
        book.setIsbn("Isbn");
        book.setId(123L);
        book.setTitle("Isbn");
        book.setPublicationDate(LocalDate.ofEpochDay(1L));

        Book book1 = new Book();
        book1.setAuthors(new HashSet<>());
        book1.setIsbn("Isbn");
        book1.setId(123L);
        book1.setTitle("Dr");
        book1.setPublicationDate(LocalDate.ofEpochDay(1L));
        assertFalse(book.equals(book1));
    }

    @Test
    void testEquals8() {
        Book book = new Book();
        book.setAuthors(new HashSet<>());
        book.setIsbn("Isbn");
        book.setId(123L);
        book.setTitle("Dr");
        book.setPublicationDate(LocalDate.ofEpochDay(0L));

        Book book1 = new Book();
        book1.setAuthors(new HashSet<>());
        book1.setIsbn("Isbn");
        book1.setId(123L);
        book1.setTitle("Dr");
        book1.setPublicationDate(LocalDate.ofEpochDay(1L));
        assertFalse(book.equals(book1));
    }
}

