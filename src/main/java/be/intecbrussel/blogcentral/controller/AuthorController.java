package be.intecbrussel.blogcentral.controller;

import be.intecbrussel.blogcentral.model.Author;
import be.intecbrussel.blogcentral.model.BlogPost;
import be.intecbrussel.blogcentral.service.AuthorService;
import be.intecbrussel.blogcentral.service.BlogpostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/authors")
public class AuthorController {
    private AuthorService authorService;
    private BlogpostService blogpostService;

    @Autowired
    public AuthorController(AuthorService authorService,
                            BlogpostService blogpostService) {
        this.authorService = authorService;
        this.blogpostService = blogpostService;
    }

    // get all Authors
    @GetMapping("")
    public String getAllAuthors(Model model){
        List<Author> authorsFromDb = authorService.getAllAuthors();
        model.addAttribute("authors", authorsFromDb);
        return "all-authors"; // placeholder
    }

    // get register-form new Author
    @GetMapping("/register")
    public String registerAuthor() {

        return "create-author"; // placeholder
    }

    // save new Author
    @PostMapping("/save")
    public String saveAuthor(@ModelAttribute("author") Author author) {
        authorService.createAuthor(author);
        return "redirect:/authors/"; // placeholder
        // TODO: redirect to page where 'create Author' was initiated
    }

    // get an Author based on id - return Author home page
    // TODO: consider username as parameter and use that as URL for
    //  Author homepage. Also, try-catch to avoid NPE / wrong format
    @GetMapping("/home")
    public String getAuthorById(@RequestParam int authorId, Model model) {
        Author author = authorService.getAuthorById(authorId);
        List<BlogPost> blogPostsFromAuthor = blogpostService.getAllBlogPostFromAuthor(author);
        model.addAttribute("postsFromAuthor", blogPostsFromAuthor);
        model.addAttribute(author);
        return "home-author"; // placeholder
    }

    // update Author - get author based on id - return author profile form
    @GetMapping("/update")
    public String showAuthorProfileForm(@RequestParam int authorId,
                                              Model model) {
        Author author = authorService.getAuthorById(authorId);
        // diagnostic println
        System.out.println(author);

        model.addAttribute("author", author);
        return "update-author";
    }

    // delete Author
    @GetMapping("/delete")
    public String deleteAuthor(@RequestParam int authorId) {
        this.authorService.deleteAuthorById(authorId);
        return "redirect:/authors/"; // placeholder
//        return "all-authors"; // placeholder
    }

}
