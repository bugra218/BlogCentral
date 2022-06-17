package be.intecbrussel.blogcentral.repository;

import be.intecbrussel.blogcentral.model.Author;
import be.intecbrussel.blogcentral.model.BlogPost;
import be.intecbrussel.blogcentral.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogpostRepository extends JpaRepository<BlogPost, Integer> {
    List<BlogPost> findByAuthor(Author author);
    List<BlogPost> findAllByTitleContaining(String title);
//    List<BlogPost> getAllByTagsContaining(String tagName);
    List<BlogPost> getAllByTagsContaining(Tag tagName);
    BlogPost findBytitle(String title);
}
