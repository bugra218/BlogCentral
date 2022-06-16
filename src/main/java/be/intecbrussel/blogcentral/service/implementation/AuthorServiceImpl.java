package be.intecbrussel.blogcentral.service.implementation;

import be.intecbrussel.blogcentral.model.Author;
import be.intecbrussel.blogcentral.repository.AuthorRepository;
import be.intecbrussel.blogcentral.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

// TODO: avoid NPE with getAuthor / getAuthorById methods
@Service
public class AuthorServiceImpl implements AuthorService {
    private AuthorRepository authorRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository,
                             BCryptPasswordEncoder passwordEncoder) {
        this.authorRepository = authorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createAuthor(Author author) throws DataIntegrityViolationException {
        author.setPassword(passwordEncoder.encode(author.getPassword()));
        authorRepository.save(author);
    }

    @Override
    public void updateAuthor(Author author) {
        author.setPassword(passwordEncoder.encode(author.getPassword()));
        authorRepository.save(author);
    }

    @Override
    public void deleteAuthor(Author author) {
        authorRepository.delete(author);
    }

    @Override
    public Author getAuthor(Author author) {
        return authorRepository.findById(author.getId()).get();
    }

    @Override
    public List<Author> getAllAuthors() {
        return (List<Author>) authorRepository.findAll();
    }

    @Override
    public Author getAuthorById(Integer id) {
        return authorRepository.findById(id).get();
    }

    @Override
    public void deleteAuthorById(Integer id) {
        this.authorRepository.deleteById(id);
    }

    @Override
    public Boolean usernameExists(String username) {
        return authorRepository.existsAuthorByUserName(username);
    }

    @Override
    public List<Author> getAllAuthorsByUsernameContaining(String username) {
        return authorRepository.findAllByUserNameContaining(username);
    }

    @Override
    public Author getAuthorByUsername(String username) {
        return authorRepository.findByUserName(username);
    }

}
