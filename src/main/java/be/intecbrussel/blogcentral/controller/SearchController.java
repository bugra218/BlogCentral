package be.intecbrussel.blogcentral.controller;

import be.intecbrussel.blogcentral.model.BlogPost;
import be.intecbrussel.blogcentral.service.AuthorService;
import be.intecbrussel.blogcentral.service.BlogpostService;
import be.intecbrussel.blogcentral.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String searchItem(@RequestParam String searchTerm, Model model) {
        List<BlogPost> postResult = blogpostService.getAllBlogpostsByTitleLike(searchTerm);
        model.addAttribute("postResult", postResult);
        return "search-result";
    }
}
