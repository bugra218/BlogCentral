package be.intecbrussel.blogcentral.service.implementation;

import be.intecbrussel.blogcentral.model.BlogPost;
import be.intecbrussel.blogcentral.model.Comment;
import be.intecbrussel.blogcentral.repository.CommentRepository;
import be.intecbrussel.blogcentral.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
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
        Timestamp ts = Timestamp.from(Instant.now());
        String ts_created = String.format("%1$Td %1$Tb %1$Ty, %1$TR", ts);
        comment.setTimestampCreatedDisplay(ts_created);
        comment.setTimestampUpdatedDisplay(ts_created);
        commentRepository.save(comment);
    }

    @Override
    public void updateComment(Comment comment) {
        Timestamp ts = Timestamp.from(Instant.now());
        comment.setTimestampUpdated(ts);
        String ts_updated = String.format("%1$Td %1$Tb %1$Ty, %1$TR", ts);
        comment.setTimestampUpdatedDisplay(ts_updated);
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
