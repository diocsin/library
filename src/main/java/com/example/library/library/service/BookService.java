package com.example.library.library.service;

import com.example.library.library.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();

    Book createNewBook(Book user);

    Book updateBook(Book user);

    Book getBookById(Long pid);

    void deleteBookById(Long pid);

    List<Book> filterBook(String filterText);
}
