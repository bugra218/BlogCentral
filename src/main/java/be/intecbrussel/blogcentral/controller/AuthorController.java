package be.intecbrussel.blogcentral.controller;

import be.intecbrussel.blogcentral.Exceptions.*;
import be.intecbrussel.blogcentral.model.Author;
import be.intecbrussel.blogcentral.model.BlogPost;
import be.intecbrussel.blogcentral.service.AuthorService;
import be.intecbrussel.blogcentral.service.BlogpostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

// TODO: improve exception handling, avoid duplicate code with help methods
@Controller
@RequestMapping("/authors")
public class AuthorController {
    private AuthorService authorService;
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

    // get register-form new Author
    @GetMapping("/register")
    public String registerAuthor(HttpServletRequest request) {
        comingFrom = request.getHeader("Referer");

        System.out.println(comingFrom);

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

            if (comingFrom != null) {
                return "redirect:" + comingFrom;
            } else return "redirect:"; // placeholder

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
    @GetMapping("/{id}")
    public String showAuthorPage(@PathVariable String id, Model model) {

        int idInt;

        try {
            idInt = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            String noNumber = id + " is not a numeric format";
            model.addAttribute("error", noNumber);
            return "error-page";
        }

        Author author;
        try {
            author = authorService.getAuthorById(idInt);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            String idNotExist = "no author with id: " + idInt;
            model.addAttribute("error", idNotExist);
            return "error-page";
        }

        List<BlogPost> blogPostsFromAuthor = blogpostService.getAllBlogPostFromAuthor(author);

        model.addAttribute(author);
        model.addAttribute("postsFromAuthor", blogPostsFromAuthor);

        return "home-author";
    }

    // update Author - get author based on id - return author profile form
    @GetMapping("/update/{id}")
    public String showAuthorProfileForm(@PathVariable String id, Model model) {

        int idInt;

        try {
            idInt = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            String noNumber = id + " is not a numeric format";
            model.addAttribute("error", noNumber);
            return "error-page";
        }

        Author author;
        try {
            author = authorService.getAuthorById(idInt);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            String idNotExist = "no author with id: " + idInt;
            model.addAttribute("error", idNotExist);
            return "error-page";
        }

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

    // help methods exception handling
//    private String convertStringIdToInt(String id, Model model) {
//        Integer idInt;
//
//        try {
//            idInt = Integer.parseInt(id);
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//            String noNumber = id + " is not in numeric format";
//            model.addAttribute("error", noNumber);
//            return "error-page";
//        }
//    }
}
