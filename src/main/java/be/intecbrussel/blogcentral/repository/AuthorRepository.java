package be.intecbrussel.blogcentral.repository;

import be.intecbrussel.blogcentral.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AuthorRepository extends JpaRepository<Author, Integer> {

    @Query("SELECT u FROM Author u WHERE u.username = :username")
    public Author getUserByname(@Param("username") String username);
}
