package be.intecbrussel.blogcentral.controller;

import be.intecbrussel.blogcentral.model.Author;
import be.intecbrussel.blogcentral.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

// TODO: test

@Controller
public class AuthorController {
    private AuthorService authorService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }
    @GetMapping("/index")
    public String index() {

        return "index.html";
    }

    // get all Authors
    @GetMapping("/")
    public String getAllAuthors(Model model){
        List<Author> authorsFromDb = authorService.getAllAuthors();
        model.addAttribute("authors", authorsFromDb);
        return "all-authors"; // placeholder
    }
    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpSession session) {
        session.setAttribute(
                "error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION")
        );
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
        return "login";
    }


    // get register-form new Author
    @GetMapping("/register")
    public String registerAuthor() {
        return "register-form"; // placeholder
    }

    // save new Author
    @PostMapping(
            value = "/register",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = {
            MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
    )
    public void addUser(@RequestParam Map<String, String> body) {
        Author author = new Author();
        author.setUsername(body.get("userName"));
        author.setPassword(bCryptPasswordEncoder.encode(body.get("password")));
        author.setFirstName(body.get("firstName"));
        author.setLastName(body.get("lastName"));
        author.setEmail(body.get("email"));
        author.setStreet(body.get("street"));
        author.setHouseNr(body.get("houseNr"));
        author.setCity(body.get("city"));
        author.setZip(Integer.parseInt(body.get("zip")));
        authorService.createAuthor(author);

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
    private String getErrorMessage(HttpServletRequest request, String key) {
        Exception exception = (Exception) request.getSession().getAttribute(key);
        String error = "";
        if (exception instanceof BadCredentialsException) {
            error = "Invalid username!";
        } else if (exception instanceof LockedException) {
            error = exception.getMessage();
        } else {
            error = "Invalid username and password!";
        }
        return error;
    }

}
