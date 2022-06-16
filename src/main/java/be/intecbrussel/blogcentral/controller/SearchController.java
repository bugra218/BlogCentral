package be.intecbrussel.blogcentral.controller;

import be.intecbrussel.blogcentral.model.Author;
import be.intecbrussel.blogcentral.model.BlogPost;
import be.intecbrussel.blogcentral.model.Tag;
import be.intecbrussel.blogcentral.service.AuthorService;
import be.intecbrussel.blogcentral.service.BlogpostService;
import be.intecbrussel.blogcentral.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("search")
public class SearchController {
    private AuthorService authorService;
    private BlogpostService blogpostService;
    private TagService tagService;

    @Autowired
    public SearchController(AuthorService authorService, BlogpostService blogpostService, TagService tagService) {
        this.authorService = authorService;
        this.blogpostService = blogpostService;
        this.tagService = tagService;
    }

    @GetMapping("")
    public String searchItem(@RequestParam (value = "search", required = false) String search, Model model) {
        List<BlogPost> postResult = blogpostService.getAllBlogpostsByTitleContaining(search);
        List<Author> authorResult = authorService.getAllAuthorsByUsernameContaining(search);

        List<Tag> matchingTags = tagService.getAllTagsByTagNameContaining(search);
        List<BlogPost> blogPostsContainingTagResult = new ArrayList<>();
        for (Tag tag : matchingTags) {
            blogPostsContainingTagResult.addAll(blogpostService.getAllBlogpostsByTagContaining(tag));
        }

        model.addAttribute("postResult", postResult);
        model.addAttribute("authorResult", authorResult);
        model.addAttribute("tagResult", blogPostsContainingTagResult);
        model.addAttribute("searchTerm", search);
        return "search-result";
    }
}