//package be.intecbrussel.blogcentral.controller;
//
//import be.intecbrussel.blogcentral.model.Author;
//import be.intecbrussel.blogcentral.model.BlogPost;
//import be.intecbrussel.blogcentral.service.AuthorService;
//import be.intecbrussel.blogcentral.service.BlogpostService;
//import be.intecbrussel.blogcentral.service.LikeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Objects;
//
//@Controller
//@RequestMapping("/like")
//public class LikeController {
//    private LikeService likeService;
//    private BlogpostService blogpostService;
//    private AuthorService authorService;
//
//    private Author loggedUser;
//
//    @Autowired
//    public LikeController(LikeService likeService, BlogpostService blogpostService, AuthorService authorService) {
//        this.likeService = likeService;
//        this.blogpostService = blogpostService;
//        this.authorService = authorService;
//        this.loggedUser = authorService.getAuthorById(3);
//    }
//
//    @GetMapping("")
//    public String getPosts(Model model) {
//        List<BlogPost> allBlogPosts = blogpostService.getAllBlogPosts();
//        model.addAttribute("blogPosts", allBlogPosts);
//        return "all-likes-blog-posts";
//    }
//
//    @GetMapping("/{postId}")
//    public String getFullPost(@PathVariable int postId, Model model) {
//        // hardcoded author for testing purpose. will have to retrieve logged in user once implemented
//        Author author = loggedUser;
//        BlogPost blogPost = blogpostService.getBlogPostById(postId);
//        int likesReceived = likeService.countLikeByBlogPost_Id(postId);
//        Boolean likedPost = likeService.UserLikedPost(blogPost, author);
//
//        model.addAttribute(author);
//        model.addAttribute(blogPost);
//        model.addAttribute("likes", likesReceived);
//        model.addAttribute("userLikedThisPost", likedPost);
//        return "like-full-blog-post";
//    }
//
//    @PostMapping("{postId}/likePost")
//    public String likePost(@PathVariable int postId, @RequestParam(value = "likeButton", required = false) String likedThisPost, @RequestParam(value = "likeButtonStatus", required = false) String likeStatus) {
//        BlogPost blogPost = blogpostService.getBlogPostById(postId);
//        Author liker = loggedUser;
//
//         // likes post if detecting changes from "noLike" to "Like"
//        if (Objects.equals(likeStatus, "false") && Objects.equals(likedThisPost, "on")) {
//            likeService.likePost(blogPost, liker);
//        }
//        // unlikes post if detecting changes from "Like" to "noLike"
//        else if (Objects.equals(likeStatus, "true") && likedThisPost == null) {
//            likeService.removeLike(blogPost, liker);
//        }
//        return "redirect:";
//    }
//}
