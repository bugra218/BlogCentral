package be.intecbrussel.blogcentral.repository;

import be.intecbrussel.blogcentral.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Boolean existsAuthorByUserName(String username);
    List<Author> findAllByUserNameContaining(String username);
}
