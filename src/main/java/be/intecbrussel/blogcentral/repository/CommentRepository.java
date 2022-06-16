package be.intecbrussel.blogcentral.repository;

import be.intecbrussel.blogcentral.model.BlogPost;
import be.intecbrussel.blogcentral.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByBlogPost(BlogPost blogPost);
    int countCommentsByBlogPost_Id(int blogPostId);
}
