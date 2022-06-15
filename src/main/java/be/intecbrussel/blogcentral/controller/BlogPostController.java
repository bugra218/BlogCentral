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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class BlogPostController {
    private BlogpostService blogpostService;
    private AuthorService authorService;
    private CommentService commentService;

    @Autowired
    public BlogPostController(BlogpostService blogpostService, AuthorService authorService, CommentService commentService) {
        this.blogpostService = blogpostService;
        this.authorService = authorService;
        this.commentService = commentService;
    }

    // this sorts ascending by date by providing a '?field=timestampCreated'
    // parameter in the url (any property can be given as param)
    @GetMapping("/home")
    public String getAllBlogPosts(@RequestParam Map<String,String> requestParams, Model model) {
        String orderBy = requestParams.get("orderBy");
        String alphabetical = requestParams.get("order");
        List<BlogPost> allBlogPosts = new ArrayList<>();

        if (orderBy == null) {
            orderBy = "timestampCreated";
        }
        if (alphabetical == null) {
            allBlogPosts.addAll(blogpostService.getAllBlogPostsDescending(orderBy));
        } else {
            allBlogPosts.addAll(blogpostService.getAllBlogPosts(orderBy));
        }
        model.addAttribute("blogPosts", allBlogPosts);
        return "index";
    }

    @GetMapping("/{postId}")
    public String getFullPost(@PathVariable int postId, Model model) {
        BlogPost blogPost = blogpostService.getBlogPostById(postId);
        // added list of comments
        List<Comment> commentsBlogPost =
                commentService.getAllCommentsForBlogPost(blogPost);
        model.addAttribute("blogPost", blogPost);
        model.addAttribute("commentsBlogPost", commentsBlogPost);
        return "home-blogpost";
    }

    @GetMapping("/writePost")
    public String createBlogPost(Model model) {
        List<Author> authors = authorService.getAllAuthors();
        model.addAttribute("authors", authors);
        return "create-blogpost";
    }

    @PostMapping("/createPost")
    public String saveBlogPost(@ModelAttribute("blogpost") BlogPost blogPost) {
        blogpostService.createBlogPost(blogPost);
        return "redirect:";
    }

    @GetMapping("/{postId}/editPost")
    public String editBlogPost(@PathVariable int postId, Model model) {
        BlogPost blogPost = blogpostService.getBlogPostById(postId);
        model.addAttribute(blogPost);
        return "update-blogpost";
    }

    @PostMapping("/{postId}/saveChanges")
    public String saveBlogPostChanges(@ModelAttribute("blogpost") BlogPost blogPost) {
        blogpostService.updateBlogPost(blogPost);
        return "redirect:./";
    }

    @GetMapping("/{postId}/delete")
    public String deleteBlogPost(@PathVariable int postId) {
        BlogPost blogPost = blogpostService.getBlogPostById(postId);
        List<Comment> commentsBlogPost =
                commentService.getAllCommentsForBlogPost(blogPost);

        for (Comment comment : commentsBlogPost) {
            commentService.deleteComment(comment);
        }

        blogpostService.deleteBlogPost(blogPost);
        return "redirect:/blogpost/";
    }
}
