package be.intecbrussel.blogcentral.repository;

import be.intecbrussel.blogcentral.model.Author;
import be.intecbrussel.blogcentral.model.BlogPost;
import be.intecbrussel.blogcentral.model.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {
    int countLikeByBlogPost_Id(int id);
    Like findLikeByBlogPostAndAuthor(BlogPost blogPost, Author author);
}
