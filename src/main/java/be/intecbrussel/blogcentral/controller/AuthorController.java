package be.intecbrussel.blogcentral.controller;

import be.intecbrussel.blogcentral.Exceptions.EmailFormatException;
import be.intecbrussel.blogcentral.Exceptions.RequiredFieldsException;
import be.intecbrussel.blogcentral.Exceptions.UsernameNotAvailableException;
import be.intecbrussel.blogcentral.Exceptions.ZipcodeException;
import be.intecbrussel.blogcentral.model.Author;
import be.intecbrussel.blogcentral.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/authors")
public class AuthorController {
    private AuthorService authorService;
    private String commingFrom = "";

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
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
    public String registerAuthor(HttpServletRequest request) {
        commingFrom = request.getHeader("Referer");
        return "create-author"; // placeholder
    }

    // save new Author
    // BindingResult result in arguments is NEEDED
    // TODO: update author profile makes use of this save as well - update
    //  should however exclude the check on username exists (separate save
    //  for update?)
    @PostMapping("/save")
    public String saveAuthor(@ModelAttribute("author") Author author, BindingResult result, RedirectAttributes attributes, HttpServletRequest request) {
        try {
            // check if all required fields are filled
            List<String> requiredFields = new ArrayList<>();
            String authorEmail = author.getEmail();
            requiredFields.add(author.getUserName());
            requiredFields.add(author.getPassword());
            requiredFields.add(author.getFirstName());
            requiredFields.add(authorEmail);
            if (requiredFields.contains("")) {
                throw new RequiredFieldsException();
            }

            // check if email format is correct
            String[] splitedEmail;
            if (authorEmail.contains("@")) {
                splitedEmail = authorEmail.split("@");
                if (!splitedEmail[1].contains(".")) {
                    throw new EmailFormatException();
                }
            } else {
                throw new EmailFormatException();
            }

            // check if zipcode is an actual number (value will default to 0 if a string was inputted instead)
            if (Objects.equals(author.getZip(), 0)) {
                throw new ZipcodeException();
            }

//             checks if inputted username is still available
            if (authorService.usernameExists(author.getUserName())) {
                throw new UsernameNotAvailableException();
            }

            authorService.createAuthor(author);
            return "redirect:" + commingFrom; // placeholder
        } catch (RequiredFieldsException | UsernameNotAvailableException | ZipcodeException | EmailFormatException e) {
            e.printStackTrace();
            attributes.addFlashAttribute( "errorMessage", e.getMessage());
            return "redirect:" + request.getHeader("Referer");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute( "errorMessage", "An unknown error occurred.");
            return "redirect:" + request.getHeader("Referer");
        }
    }

    // get an Author based on id - return Author home page
    //  TODO: strange NrFormatException when creating new Author.
    //   PathVariable is 'null'
    @GetMapping("/{id}")
    public String showAuthorPage(@PathVariable String id, Model model) {
        Integer idInt = Integer.parseInt(id);
        Author author = authorService.getAuthorById(idInt);
        model.addAttribute(author);
        return "home-author"; // placeholder
    }

    // update Author - get author based on id - return author profile form
    @GetMapping("/update/{id}")
    public String showAuthorProfileForm(@PathVariable int id, Model model) {
//        Integer idInt = Integer.parseInt(id);
        Author author = authorService.getAuthorById(id);
        System.out.println(author);
        model.addAttribute("author", author);
        return "update-author";
    }

    // delete Author
    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable String id) {
        Integer idInt = Integer.parseInt(id);
        this.authorService.deleteAuthorById(idInt);
        return "redirect:/authors/"; // placeholder
    }
}
