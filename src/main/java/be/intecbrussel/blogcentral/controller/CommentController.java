package be.intecbrussel.blogcentral.controller;

import be.intecbrussel.blogcentral.model.BlogPost;
import be.intecbrussel.blogcentral.model.Comment;
import be.intecbrussel.blogcentral.service.AuthorService;
import be.intecbrussel.blogcentral.service.BlogpostService;
import be.intecbrussel.blogcentral.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CommentController {

    private CommentService commentService;
    private BlogpostService blogpostService;
    private AuthorService authorService;

    @Autowired
    public CommentController(CommentService commentService,
                             BlogpostService blogpostService,
                             AuthorService authorService) {
        this.commentService = commentService;
        this.blogpostService = blogpostService;
        this.authorService = authorService;
    }

    // method to create a new comment - 'add comment' @BlogPost id ->
    // BlogPostService needed - comment bound to Author -> AuthorService needed
    @GetMapping("/blogpost/createComment") // placeholder
    public String addComment(@RequestParam int postId, Model model) {
        BlogPost blogPost = blogpostService.getBlogPostById(postId);

        Comment comment = new Comment();
        comment.setAuthor(blogPost.getAuthor());
        comment.setBlogPost(blogPost);

        // diagnostic println
        System.out.println(comment);

        model.addAttribute("comment", comment);
        return "create-comment";
    }

    // method to save a comment
    @PostMapping("/blogpost/saveComment")
    public String saveComment(@ModelAttribute Comment comment) {
        commentService.createComment(comment);
        return "redirect:/full-blog-post";
    }

    // method to collect an individual comment by id, to update / delete
    @GetMapping("/blogpost/getComment")
    public String getCommentById(@RequestParam int commentId, Model model) {
        Comment comment = commentService.getCommentById(commentId);

        // diagnostic println
        System.out.println(comment);

        model.addAttribute("comment", comment);
        return "update-comment";
    }

    // method to delete a comment - navigate to specific comment
    // with id - bound to user -> AuthorService needed
    @GetMapping("/blogpost/deleteComment")
    public String deleteComment(@RequestParam int commentId, Model model) {
        Comment comment = commentService.getCommentById(commentId);

        // diagnostic println
        System.out.println(comment);

        commentService.deleteComment(comment);
        return "full-blog-post"; // TODO: redirect:full-blog-post with id?
    }

}
