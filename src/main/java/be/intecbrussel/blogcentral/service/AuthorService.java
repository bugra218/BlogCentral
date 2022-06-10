package be.intecbrussel.blogcentral.service;

import be.intecbrussel.blogcentral.model.Author;
import be.intecbrussel.blogcentral.model.BlogPost;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface AuthorService {
    void createAuthor(Author author) throws DataIntegrityViolationException;
    void updateAuthor(Author author);
    void deleteAuthor(Author author);
    Author getAuthor(Author author);
    List<Author> getAllAuthors();
    List<Author> getAllAuthorsByUsernameContaining(String title);
    Author getAuthorById(Integer id);
    void deleteAuthorById(Integer id);
    Boolean usernameExists(String username);
}
