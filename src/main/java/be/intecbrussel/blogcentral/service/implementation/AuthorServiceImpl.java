package be.intecbrussel.blogcentral.service.implementation;

import be.intecbrussel.blogcentral.model.Author;
import be.intecbrussel.blogcentral.repository.AuthorRepository;
import be.intecbrussel.blogcentral.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// TODO: avoid NPE with getAuthor / getAuthorById methods
@Service
public class AuthorServiceImpl implements AuthorService {
    private AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void createAuthor(Author author) {
        authorRepository.save(author);
    }

    @Override
    public void updateAuthor(Author author) {
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

    // added to enable find by id in Controller
    @Override
    public Author getAuthorById(Integer id) {
        return authorRepository.findById(id).get();
    }

    @Override
    public void deleteAuthorById(Integer id) {
        this.authorRepository.deleteById(id);
    }

}
