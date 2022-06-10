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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("{searchTerm}")
    public String searchItem(@PathVariable String searchTerm,
                             RedirectAttributes redirect) {

        System.out.println(searchTerm);

        List<BlogPost> postResult =
                blogpostService.getAllBlogpostsByTitleContaining(searchTerm);
        List<Author> authorResult =
                authorService.getAllAuthorsByUsernameContaining(searchTerm);
        List<Tag> tagResult =
                tagService.getAllTagsByTagNameContaining(searchTerm);

        redirect.addFlashAttribute("postResult", postResult);
        redirect.addFlashAttribute("authorResult", authorResult);
        redirect.addFlashAttribute("tagResult", tagResult);
        return "redirect:result";
    }

    @GetMapping("/result")
    public String showSearch() {
        return "search-result";
    }



}
