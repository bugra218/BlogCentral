package be.intecbrussel.blogcentral.controller;

import be.intecbrussel.blogcentral.service.ActiveUserStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

@Controller
public class UserController {
    @Autowired
    ActiveUserStore activeUserStore;

    @GetMapping("/loggedUsers")
    public String getLoggedUsers(Locale locale, Model model) {
        model.addAttribute("users", activeUserStore.getUsers());
        return "users";
    }
}
