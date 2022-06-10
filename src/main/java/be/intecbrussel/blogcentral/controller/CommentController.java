package be.intecbrussel.blogcentral.controller;

import be.intecbrussel.blogcentral.model.Author;
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

//TODO: update + urls are a complicated mess.. possible to annotate the class
// with variable postId in mapping? check

@Controller
//@RequestMapping(value = "/blogpost/{postId}")
public class CommentController {

    private CommentService commentService;
    private BlogpostService blogpostService;
    private AuthorService authorService;

    private BlogPost blogPost; // it would be great to set the specific
    // blogpost when CommentController is created. but how?

    @Autowired
    public CommentController(CommentService commentService,
                             BlogpostService blogpostService,
                             AuthorService authorService) {
        this.commentService = commentService;
        this.blogpostService = blogpostService;
        this.authorService = authorService;
    }

    // method to create a new comment
    @GetMapping(value = "/blogpost/{postId}/writeComment") // placeholder
    public String addComment(@PathVariable int postId, Model model) {

        List<Author> authors = authorService.getAllAuthors(); // temporary,
        // to allow selecting author for a comment. When security is
        // implemented, we should get the logged user automatically
        BlogPost blogPost = blogpostService.getBlogPostById(postId);

        model.addAttribute("authors", authors);
        model.addAttribute("blogPost", blogPost);

        return "create-comment";
    }

    // method to save a new comment
    @PostMapping(value = "/blogpost/{postId}/saveComment")
    public String saveComment(@PathVariable int postId,
                              @ModelAttribute("comment") Comment comment) {

        BlogPost blogPost = blogpostService.getBlogPostById(postId);
        comment.setBlogPost(blogPost);

        commentService.createComment(comment);

        return "redirect:/blogpost/" + postId;
    }

    // method to collect an individual comment by id, to update / delete
    @GetMapping(value = "/blogpost/{postId}/updateComment/{commentId}")
    public String showComment(@PathVariable int commentId,
                              Model model) {
        Comment comment = commentService.getCommentById(commentId);
        int postId = comment.getBlogPost().getId();
        BlogPost blogPost = blogpostService.getBlogPostById(postId);

        // diagnostic println
        System.out.println(comment);

        model.addAttribute("comment", comment);
        model.addAttribute("blogPost", blogPost);

        return "update-comment";
    }

    // method to save updated comment
    @PostMapping(value = "/saveUpdatedComment")
    public String saveUpdatedComment(@ModelAttribute("comment") Comment comment) {

        int postId = comment.getBlogPost().getId();

        commentService.updateComment(comment);

        return "redirect:/blogpost/" + postId;
    }

    // method to delete a comment
    @GetMapping(value = "/deleteComment/{commentId}")
    public String deleteComment(@PathVariable int commentId) {

        Comment comment = commentService.getCommentById(commentId);
        int postId = comment.getBlogPost().getId();

        commentService.deleteComment(comment);
        return "redirect:/blogpost/" + postId;
    }
}
