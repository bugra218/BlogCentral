package be.intecbrussel.blogcentral.service.implementation;

import be.intecbrussel.blogcentral.model.BlogPost;
import be.intecbrussel.blogcentral.model.Comment;
import be.intecbrussel.blogcentral.repository.CommentRepository;
import be.intecbrussel.blogcentral.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void createComment(Comment comment) {
        java.util.Date date = new java.util.Date();
        Date now = new Date(date.getTime());
        comment.setTimestampCreated(now);
        commentRepository.save(comment);
    }

    @Override
    public void updateComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public List<Comment> getAllCommentsForBlogPost(BlogPost blogPost) {
        return commentRepository.findByBlogPost(blogPost);
    }

    @Override
    public Comment getCommentById(int commentId) {
        return commentRepository.findById(commentId).get();
    }
}
