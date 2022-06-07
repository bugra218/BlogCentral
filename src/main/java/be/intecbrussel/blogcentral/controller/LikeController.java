package be.intecbrussel.blogcentral.controller;

import be.intecbrussel.blogcentral.model.BlogPost;
import be.intecbrussel.blogcentral.repository.BlogpostRepository;
import be.intecbrussel.blogcentral.repository.LikeRepository;
import be.intecbrussel.blogcentral.service.BlogpostService;
import be.intecbrussel.blogcentral.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/like")
public class LikeController {
    private LikeService likeService;
    private BlogpostService blogpostService;

    @Autowired
    public LikeController(LikeService likeService, BlogpostService blogpostService) {
        this.likeService = likeService;
        this.blogpostService = blogpostService;
    }

    @GetMapping("")
    public String getPosts(Model model) {
        List<BlogPost> allBlogPosts = blogpostService.getAllBlogPosts();
        model.addAttribute("blogPosts", allBlogPosts);
        return "all-blog-posts-like";
    }

    @GetMapping("/{postId}")
    public String getFullPost(@PathVariable int postId, Model model) {
        BlogPost blogPost = blogpostService.getBlogPostById(postId);
        model.addAttribute(blogPost);
        return "like-test";
    }

//    @PostMapping(name = "likePost")
//    public void likePost() {
//
//    }
}
