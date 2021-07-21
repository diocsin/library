package com.example.library.library.repository;

import com.example.library.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b join fetch b.author")
    List<Book> findAll();

    @Query("select b from Book b join fetch b.author where b.pid = :id")
    Book findBookById(@Param("id") Long pid);
}
