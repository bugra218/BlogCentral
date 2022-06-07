package be.intecbrussel.blogcentral.controller;

import be.intecbrussel.blogcentral.model.Author;
import be.intecbrussel.blogcentral.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

// TODO: test

@Controller
@RequestMapping("/authors")
public class AuthorController {
    private AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // get all Authors
    @GetMapping("/")
    public String getAllAuthors(Model model){
        List<Author> authorsFromDb = authorService.getAllAuthors();
        model.addAttribute("authors", authorsFromDb);
        return "all-authors"; // placeholder
    }

    // get register-form new Author
    @GetMapping("/register")
    public String registerAuthor() {
        return "register-form"; // placeholder
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
    @GetMapping("/{id}")
    public String showAuthorPage(@PathVariable String id, Model model) {
        Integer idInt = Integer.parseInt(id);
        Author author = authorService.getAuthorById(idInt);
        model.addAttribute(author);
        return "author-page"; // placeholder
    }

    // update Author - get author based on id - return author profile form
    @GetMapping("/update")
    public String showAuthorProfileForm(@RequestParam int id,
                                              Model model) {
//        Integer idInt = Integer.parseInt(id);
        Author author = authorService.getAuthorById(id);
        System.out.println(author);
        model.addAttribute("author", author);
        return "update-form";
    }

    // delete Author
    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable String id) {
        Integer idInt = Integer.parseInt(id);
        this.authorService.deleteAuthorById(idInt);
        return "redirect:/authors/"; // placeholder
    }

}
