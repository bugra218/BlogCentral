package be.intecbrussel.blogcentral.controller;

import be.intecbrussel.blogcentral.model.Author;
import be.intecbrussel.blogcentral.model.BlogPost;
import be.intecbrussel.blogcentral.service.AuthorService;
import be.intecbrussel.blogcentral.service.BlogpostService;
import be.intecbrussel.blogcentral.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Controller
public class LikeController {
    private LikeService likeService;
    private BlogpostService blogpostService;
    private AuthorService authorService;

    @Autowired
    public LikeController(LikeService likeService, BlogpostService blogpostService, AuthorService authorService) {
        this.likeService = likeService;
        this.blogpostService = blogpostService;
        this.authorService = authorService;
    }

    @GetMapping("")
    public String getPosts(@RequestParam(name = "field", required = false, defaultValue = "timestampCreated") String field, Model model) {
        List<BlogPost> allBlogPosts = blogpostService.getAllBlogPosts(field);
        model.addAttribute("blogPosts", allBlogPosts);
        return "all-likes-blog-posts";
    }

    @GetMapping("blogpost/{postId}/likePost")
    public String likePost(@PathVariable int postId) {
        BlogPost blogPost = blogpostService.getBlogPostById(postId);
        String currentUserName = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        Author liker = authorService.getAuthorByUsername(currentUserName);
        if (!likeService.UserLikedPost(blogPost, liker)) {
            likeService.likePost(blogPost, liker);
        } else {
            likeService.removeLike(blogPost, liker);
        }
        return "redirect:./";
    }
}