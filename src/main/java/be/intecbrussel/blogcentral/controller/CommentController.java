package be.intecbrussel.blogcentral.controller;

import be.intecbrussel.blogcentral.model.BlogPost;
import be.intecbrussel.blogcentral.model.Comment;
import be.intecbrussel.blogcentral.service.BlogpostService;
import be.intecbrussel.blogcentral.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CommentController {

    private CommentService commentService;
    private BlogpostService blogpostService;

    @Autowired
    public CommentController(CommentService commentService,
                             BlogpostService blogpostService) {
        this.commentService = commentService;
        this.blogpostService = blogpostService;
    }

    @GetMapping("/blogpostComments") // placeholder. TODO: incl. in Blogpost
    public String getAllCommentsForBlogPost(@RequestParam int postId,
                                            Model model) {
        BlogPost blogPost = blogpostService.getBlogPostById(postId);
        List<Comment> commentsFromDB =
                commentService.getAllCommentsForBlogPost(blogPost);
        model.addAttribute("comments", commentsFromDB);
        model.addAttribute("blogpost", blogPost);

        return "blogpost"; //placeholder
    }

    // getMapping /blogpost{id}
    // method getOnePost (int postId)
    // blogPost = blogPost(postid)
    // list comments = getcomments for post
    // add attribute blogpost
    // add attribute comments
    // return page



    // method to create a new comment - 'add comment' @BlogPost id ->
    // BLogPostService needed - comment bound to Author -> AuthorService needed

    // method to update an existing comment - navigate to specific comment
    // with id - bound to user -> AuthorService needed

    // method to delete a comment - navigate to specific comment
    // with id - bound to user -> AuthorService needed


}
