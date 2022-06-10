package be.intecbrussel.blogcentral.service;

import be.intecbrussel.blogcentral.model.Author;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface AuthorService {
    void createAuthor(Author author) throws DataIntegrityViolationException;
    void updateAuthor(Author author);
    void deleteAuthor(Author author);
    Author getAuthor(Author author);
    List<Author> getAllAuthors();

    // added to allow finding Author per id
    Author getAuthorById(Integer id);
    void deleteAuthorById(Integer id);
}
