package by.ilyushenko.library.service;

import by.ilyushenko.library.models.Book;
import by.ilyushenko.library.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.OptionalInt;

@Service
public class BookService {

    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Page<Book> findPaginated(Pageable pageable) {
        Page<Book> books = bookRepository.findAll(pageable);
        return books;
    }

    public Page<Book> findByNameContainingIgnoreCase(String search, Pageable pageable) {
        Page<Book> books = bookRepository.searchBook("%" + search + "%", pageable);
        return books;
    }


    public Optional<Book> findById(Integer id) {
        Optional<Book> book = bookRepository.findById(id);
        return book;
    }

    public void addNew(Book book) {
        bookRepository.save(book);
    }

    public void update(Book book) {
        bookRepository.save(book);
    }

}
