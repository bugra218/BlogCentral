package be.intecbrussel.blogcentral.service;

import be.intecbrussel.blogcentral.model.BlogPost;
import be.intecbrussel.blogcentral.model.Comment;

import java.util.List;

public interface CommentService {
    void createComment(Comment comment);
    void updateComment(Comment comment);
    void deleteComment(Comment comment);
    List<Comment> getAllCommentsForBlogPost(BlogPost blogPost);
}
