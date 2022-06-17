package be.intecbrussel.blogcentral.controller;

import be.intecbrussel.blogcentral.model.Author;
import be.intecbrussel.blogcentral.model.BlogPost;
import be.intecbrussel.blogcentral.model.Comment;
import be.intecbrussel.blogcentral.model.Tag;
import be.intecbrussel.blogcentral.service.AuthorService;
import be.intecbrussel.blogcentral.service.BlogpostService;
import be.intecbrussel.blogcentral.service.CommentService;
import be.intecbrussel.blogcentral.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@Controller
public class BlogPostController {
    private BlogpostService blogpostService;
    private AuthorService authorService;
    private CommentService commentService;
    private TagService tagService;

    @Autowired
    public BlogPostController(BlogpostService blogpostService, AuthorService authorService, CommentService commentService, TagService tagService) {
        this.blogpostService = blogpostService;
        this.authorService = authorService;
        this.commentService = commentService;
        this.tagService = tagService;
    }

    // this sorts ascending by date by providing a '?field=timestampCreated'
    // parameter in the url (any property can be given as param)
    @GetMapping("/home")
    public String getAllBlogPosts() {
        return "redirect:/home/page/1";
    }

    @GetMapping("/home/page/{pageNumber}")
    public String getOnePage(@RequestParam(name = "orderBy", required = false, defaultValue = "timestampCreated") String orderBy, Model model, @PathVariable("pageNumber") int currentPage) {
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

    @GetMapping("/blogpost/{postId}")
    public String getFullPost(@PathVariable int postId, Model model) {
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
        String currentUserName = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        Author authorDB = authorService.getAuthorByUsername(currentUserName);
        List<Tag> allTags = tagService.getAllTags();
        System.out.println(allTags.size());
        // checks what tags are selected on specific post for checking corresponding checkboxes

        model.addAttribute("allTags", allTags);
        model.addAttribute("author", authorDB);
        return "create-blogpost";
    }

    @PostMapping("/createPost")
    public String saveBlogPost(@ModelAttribute("blogpost") BlogPost blogPost ,@RequestParam(name="tags", required = false) List<Tag> tags) {
        // added authorId to be able to add it in redirect
        String currentUserName = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        Author authorDB = authorService.getAuthorByUsername(currentUserName);
        int authorId = authorDB.getId();

        blogpostService.createBlogPost(blogPost);
        int blogPostId = blogPost.getId();
        BlogPost blogPostTags = blogpostService.getBlogPostById(blogPostId);
        tagService.addTagsToPost(tags, blogPost);

        // changed redirect for testing purposes
        return "redirect:/authors/" + authorId;
    }

    @GetMapping("/blogpost/{postId}/editpost")
    public String editBlogPost(@PathVariable int postId, Model model) {
        BlogPost blogPost = blogpostService.getBlogPostById(postId);
        List<Tag> allTags = tagService.getAllTags();



        model.addAttribute(blogPost);
        model.addAttribute("allTags", allTags);
        return "full-blog-post-addtags";
    }

    @PostMapping("/savedchanges")
    public String saveChanges(@ModelAttribute("blogpost") BlogPost blogPost, @RequestParam(name="tags", required = false) List<Tag> tags) {
        blogpostService.updateBlogPost(blogPost);
        BlogPost blogPostSave = blogpostService.getBlogPostById(blogPost.getId());
        tagService.addTagsToPost(tags, blogPostSave);

        return "redirect:/login";
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
