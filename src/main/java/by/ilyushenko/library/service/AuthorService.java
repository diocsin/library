package by.ilyushenko.library.service;

import by.ilyushenko.library.models.Author;
import by.ilyushenko.library.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAll() {
        return (List<Author>) authorRepository.findAll();
    }
}
