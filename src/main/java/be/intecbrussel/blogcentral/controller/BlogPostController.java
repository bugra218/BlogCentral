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

@Controller
@RequestMapping("blogpost")
public class BlogPostController {
    private BlogpostService blogpostService;
    private AuthorService authorService;
    private CommentService commentService;

    @Autowired
    public BlogPostController(BlogpostService blogpostService,
                              AuthorService authorService,
                              CommentService commentService) {
        this.blogpostService = blogpostService;
        this.authorService = authorService;
        this.commentService = commentService;
    }

    @GetMapping("")
    public String getAllBlogPosts(Model model) {
        model.addAttribute("blogPosts", blogpostService.getAllBlogPosts());
        return "home-blogcentral";
    }

    @GetMapping("/{postId}")
    public String getFullPost(@PathVariable int postId, Model model) {
        BlogPost blogPost = blogpostService.getBlogPostById(postId);
        // added list of comments
        List<Comment> commentsBlogPost =
                commentService.getAllCommentsForBlogPost(blogPost);
        model.addAttribute(blogPost);
        model.addAttribute(commentsBlogPost);
        return "home-blogpost";
    }

    // getting all posts from Author is taken care of in AuthorController
//    @GetMapping("/{authorId}/posts")
//    public String getAllPostsFromUser(@PathVariable int authorId, Model model) {
//        Author author = authorService.getAuthorById(authorId);
//        List<BlogPost> blogPostsFromAuthor = blogpostService.getAllBlogPostFromAuthor(author);
//        model.addAttribute("postsFromAuthor", blogPostsFromAuthor);
//        return "all-blog-posts-from-author";
//    }

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

    @PostMapping("/{postId}/delete")
    public String deleteBlogPost(@PathVariable int postId) {
        BlogPost blogPost = blogpostService.getBlogPostById(postId);
        blogpostService.deleteBlogPost(blogPost);
        return "redirect:../";
    }
}
