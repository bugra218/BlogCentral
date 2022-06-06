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
        return "all-authors";
    }

    // get register-form new Author
    // is it needed to bind this to a new Author? if not, why not?
    @GetMapping("/register")
    public String registerAuthor() {
        return "author-profile-form"; // placeholder
    }

    // save new Author
    @PostMapping("/save")
    public String saveAuthor(@ModelAttribute("author") Author author) {
        authorService.createAuthor(author);
        return "redirect:all-authors"; // placeholder
        // TODO: redirect to page where 'create Author' was initiated
    }

    // get an Author based on id - return Author home page
    // TODO: consider to give username as parameter and use that as URL for
    //  Author homepage
    @GetMapping("/{id}")
    public String showAuthorPage(@PathVariable String id, Model model) {
        Integer idInt = Integer.parseInt(id);
        Author author = authorService.getAuthorById(idInt);
        model.addAttribute(author);
        return "authorPage"; // placeholder
    }

    // update Author - get author based on id - return author profile form
    // prefilled with author details - TODO: not sure if this way works, how
    //  will the fields be pre-filled?
    @GetMapping("/update/{id}")
    public String showAuthorProfileForm(@PathVariable String id,
                                              Model model) {
        Integer idInt = Integer.parseInt(id);
//        ModelAndView mav = new ModelAndView("author-profile-form");
        Author author = authorService.getAuthorById(idInt);
        model.addAttribute(author); // placeholder
        return "author-profile-form";
    }

    // delete Author
    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable String id) {
        Integer idInt = Integer.parseInt(id);
        this.authorService.deleteAuthorById(idInt);
        return "redirect:/"; // placeholder
    }

}
