package com.example.library.library.service.impl;

import com.example.library.library.model.Book;
import com.example.library.library.repository.BookRepository;
import com.example.library.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book createNewBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book getBookById(Long pid) {
        return bookRepository.findBookById(pid);
    }

    @Override
    public void deleteBookById(Long pid) {
        bookRepository.deleteById(pid);
    }

    @Override
    public List<Book> filterBook(String filterText) {
        return null;
    }
}
