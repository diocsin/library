package by.ilyushenko.library.repository;

import by.ilyushenko.library.models.Author;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AuthorRepository extends CrudRepository<Author, Integer> {

}
