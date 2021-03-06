package be.intecbrussel.blogcentral.controller;

import be.intecbrussel.blogcentral.model.BlogPost;
import be.intecbrussel.blogcentral.model.Tag;
import be.intecbrussel.blogcentral.service.BlogpostService;
import be.intecbrussel.blogcentral.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/tag")
public class TagController {
    private TagService tagService;
    private BlogpostService blogpostService;

    @Autowired
    public TagController(TagService tagService, BlogpostService blogpostService) {
        this.tagService = tagService;
        this.blogpostService = blogpostService;
    }

    @GetMapping("/")
    public String getAllTags(Model model) {
        List<Tag> tags = tagService.getAllTags();
        model.addAttribute("tags", tags);
        return "all-tags";
    }

    @GetMapping("/create")
    public String createNewTag() {
        return "create-tag";
    }

    @PostMapping("/save")
    public String saveNewTag(@ModelAttribute("tag") Tag tag) {
        tagService.createTag(tag);
        return "redirect:/";
    }

    @GetMapping("/{tagId}")
    public String getFullTag(@PathVariable int tagId, Model model) {
        Tag tag = tagService.getTagById(tagId);
        model.addAttribute(tag);
        return "full-tag-page";
    }

    @PostMapping("/{tagId}/delete")
    public String deleteExistingTag(@PathVariable int tagId) {
        Tag tagToBeDeleted = tagService.getTagById(tagId);
        tagService.deleteTag(tagToBeDeleted);
        return "redirect:./../";
    }

    @GetMapping("/allPosts")
    public String getAllBlogPosts(@RequestParam(name="field", required = false, defaultValue = "timestampCreated")String field, Model model) {
        model.addAttribute("blogPosts", blogpostService.getAllBlogPosts(field));
        return "all-blog-posts-addtags";
    }

//    @GetMapping("/allPosts/{postId}")
//    public String getFullPost(@PathVariable int postId, Model model) {
//        BlogPost blogPost = blogpostService.getBlogPostById(postId);
//        List<Tag> allTags = tagService.getAllTags();
//
//
//
//        model.addAttribute(blogPost);
//        model.addAttribute("allTags", allTags);
//        return "full-blog-post-addtags";
//    }

    @PostMapping("/allPosts/{postId}/addTagsToPost")
    public String addTagsToPost(@PathVariable int postId, @RequestParam(name="tags", required = false) List<Tag> tags) {
        BlogPost blogPost = blogpostService.getBlogPostById(postId);
        tagService.addTagsToPost(tags, blogPost);
        return "redirect:./../";
    }
}
