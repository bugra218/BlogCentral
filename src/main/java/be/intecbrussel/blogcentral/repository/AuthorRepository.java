package be.intecbrussel.blogcentral.repository;

import be.intecbrussel.blogcentral.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
