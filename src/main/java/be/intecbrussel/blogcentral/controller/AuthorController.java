package be.intecbrussel.blogcentral.controller;


import be.intecbrussel.blogcentral.Exceptions.UsernameNotAvailableException;
import be.intecbrussel.blogcentral.model.Author;
import be.intecbrussel.blogcentral.model.BlogPost;
import be.intecbrussel.blogcentral.service.AuthorService;
import be.intecbrussel.blogcentral.service.BlogpostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

// TODO: exception handling, avoid duplicate code with help methods
@Controller
@RequestMapping("/authors")
public class AuthorController {

    private AuthorService authorService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private BlogpostService blogpostService;
    private String comingFrom = "";

    @Autowired
    public AuthorController(AuthorService authorService, BlogpostService blogpostService) {
        this.authorService = authorService;
        this.blogpostService = blogpostService;
    }

    // get all Authors
    @GetMapping("")
    public String getAllAuthors(Model model){
        List<Author> authorsFromDb = authorService.getAllAuthors();

        if (authorsFromDb == null) {
            model.addAttribute("error", "no authors found");
            return "error-page";
        }

        model.addAttribute("authors", authorsFromDb);
        return "all-authors"; // placeholder
    }

    @GetMapping("/register")
    public String registerAuthor(Model model) {
        model.addAttribute("author", new Author());
        return "create-author"; // placeholder
    }

    @PostMapping("/save")
    public String saveAuthor(@Valid @ModelAttribute("author") Author author, BindingResult result, Model model, RedirectAttributes attributes, HttpServletRequest request) {

        try {
            if (authorService.usernameExists(author.getUserName())) {
                throw new UsernameNotAvailableException();
            }
        } catch (UsernameNotAvailableException e) {
            e.printStackTrace();
            attributes.addFlashAttribute( "errorMessage", e.getMessage());
            return "redirect:" + request.getHeader("Referer");
        }

        if(result.hasErrors()) {
            model.addAttribute("author", author);
            return "create-author";
        }
        authorService.createAuthor(author);
        return "redirect:/"; // placeholder
    }

    // get an Author based on id - return Author home page
    @GetMapping("/{id}")
    public String showAuthorPage(@PathVariable String id, Model model) {

        // call help method to check if path param can be converted to number
        int idInt = convertStringIdToInt(id);

        // help method returns 0 if conversion to nr. didn't work
        if (idInt == 0) {
            String noNumber = id + " is not a numeric format";
            model.addAttribute("error", noNumber);
            return "error-page";
        }

        // call help method to check if there is an author for provided id
        Author authorDB = checkAuthorIdExists(idInt);

        // help method returns null if authorId can not be found
        if (authorDB == null) {
            String idNotExist = "no author with id: " + idInt;
            model.addAttribute("error", idNotExist);
            return "error-page";
        }

        // collect blogposts for author
        List<BlogPost> blogPostsFromAuthor = blogpostService.getAllBlogPostFromAuthor(authorDB);

        model.addAttribute(authorDB);
        model.addAttribute("postsFromAuthor", blogPostsFromAuthor);

        return "authors";
    }

    // update Author - get author based on id - return author profile form
    @GetMapping("/update/{id}")
    public String showAuthorProfileForm(@PathVariable String id, Model model) {

        // call help method to check if path param can be converted to number
        int idInt = convertStringIdToInt(id);

        // help method returns 0 if conversion to nr. didn't work
        if (idInt == 0) {
            String noNumber = id + " is not a numeric format";
            model.addAttribute("error", noNumber);
            return "error-page";
        }

        // call help method to check if there is an author for provided id
        Author authorDB = checkAuthorIdExists(idInt);

        // help method returns null if authorId can not be found
        if (authorDB == null) {
            String idNotExist = "no author with id: " + idInt;
            model.addAttribute("error", idNotExist);
            return "error-page";
        }

        model.addAttribute("author", authorDB);
        return "update-author";
    }

    // delete Author
    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable String id, Model model) {

        int idInt = convertStringIdToInt(id);

        // help method returns 0 if conversion to nr. didn't work
        if (idInt == 0) {
            String noNumber = id + " is not a numeric format";
            model.addAttribute("error", noNumber);
            return "error-page";
        }

        Author authorDB = checkAuthorIdExists(idInt);

        // help method returns null if authorId can not be found
        if (authorDB == null) {
            String idNotExist = "no author with id: " + idInt;
            model.addAttribute("error", idNotExist);
            return "error-page";
        }

        List<BlogPost> blogPostsForAuthor =
                blogpostService.getAllBlogPostFromAuthor(authorDB);
        for (BlogPost blogPost : blogPostsForAuthor) {
            blogpostService.deleteBlogPost(blogPost);
        }

        this.authorService.deleteAuthorById(idInt);
        return "redirect:/authors/"; // placeholder
    }

    // help methods exception handling
    private int convertStringIdToInt(String id) {
        int idInt;

        try {
            idInt = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }

        return idInt;
    }

    private Author checkAuthorIdExists(int id) {
        Author authorDB;

        try {
            authorDB = authorService.getAuthorById(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return null;
        }

        return authorDB;
    }
}
