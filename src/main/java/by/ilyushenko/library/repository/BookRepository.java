package by.ilyushenko.library.repository;

import by.ilyushenko.library.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends PagingAndSortingRepository<Book, Integer> {

    @Query("select b from Book b where b.name like :x")
    Page<Book> searchBook(@Param("x") String name, Pageable pageable);

    List<Book> findAllByName(String name, Pageable pageable);


}
