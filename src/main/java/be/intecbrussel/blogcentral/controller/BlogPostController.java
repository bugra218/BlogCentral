package be.intecbrussel.blogcentral.controller;

import be.intecbrussel.blogcentral.model.Author;
import be.intecbrussel.blogcentral.model.BlogPost;
import be.intecbrussel.blogcentral.model.Comment;
import be.intecbrussel.blogcentral.service.AuthorService;
import be.intecbrussel.blogcentral.service.BlogpostService;
import be.intecbrussel.blogcentral.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public String getAllBlogPosts() {
        return "redirect:/home/page/1";
    }

    @GetMapping("/home/page/{pageNumber}")
    public String getOnePage(@RequestParam(name = "orderBy", required = false, defaultValue = "recent") String orderBy, Model model, @PathVariable("pageNumber") int currentPage) {
        Page<BlogPost> page = blogpostService.findPage(currentPage, orderBy);
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<BlogPost> BlogPosts = page.getContent();


        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("blogPosts", BlogPosts);
        model.addAttribute("activeFilter", orderBy);

        return "home";
    }

    // example for getting the author in case needed
//    public String showAuthorProfileForm(Model model) {
//        String currentUserName = SecurityContextHolder.getContext()
//                .getAuthentication()
//                .getName();
//        Author authorDB = authorService.getAuthorByUsername(currentUserName);
//        model.addAttribute("author", authorDB);
//        return "update-author";
//    }

    @GetMapping("/blogpost/{postId}")
    public String getFullPost(@PathVariable int postId, Model model) {

//        String currentUserName = SecurityContextHolder.getContext()
//                .getAuthentication()
//                .getName();
//        Author authorDB = authorService.getAuthorByUsername(currentUserName);
//        model.addAttribute("author", authorDB);

        BlogPost blogPost = blogpostService.getBlogPostById(postId);
        // added list of comments
        List<Comment> commentsBlogPost =
                commentService.getAllCommentsForBlogPost(blogPost);
        model.addAttribute("blogPost", blogPost);
        model.addAttribute("commentsBlogPost", commentsBlogPost);
        return "blogpost";
    }

    @GetMapping("/writePost")
    public String createBlogPost(Model model) {
        List<Author> authors = authorService.getAllAuthors();
        model.addAttribute("authors", authors);
        return "create-blogpost";
    }

    @PostMapping("/createPost")
    public String saveBlogPost(@ModelAttribute("blogpost") BlogPost blogPost) {
        // added authorId to be able to add it in redirect
        int authorId = blogPost.getAuthor().getId();
        blogpostService.createBlogPost(blogPost);
        // changed redirect for testing purposes
        return "redirect:/authors/" + authorId;
    }

    @GetMapping("/{postId}/editPost")
    public String editBlogPost(@PathVariable int postId, Model model) {
        BlogPost blogPost = blogpostService.getBlogPostById(postId);
        model.addAttribute(blogPost);
        return "update-blogpost";
    }

    @PostMapping("/{postId}/saveChanges")
    public String saveBlogPostChanges(@ModelAttribute("blogpost") BlogPost blogPost) {
        int postId = blogPost.getId();
        blogpostService.updateBlogPost(blogPost);
        return "redirect:/blogpost/" + postId +"/"; // added "/"
    }

    @GetMapping("/{postId}/delete")
    public String deleteBlogPost(@PathVariable int postId) {
        BlogPost blogPost = blogpostService.getBlogPostById(postId);
        int authorId = blogPost.getAuthor().getId();
        List<Comment> commentsBlogPost =
                commentService.getAllCommentsForBlogPost(blogPost);

        for (Comment comment : commentsBlogPost) {
            commentService.deleteComment(comment);
        }

        blogpostService.deleteBlogPost(blogPost);
        return "redirect:/authors/" + authorId;
    }
}
