package com.example.library.library.controller;

import com.example.library.library.service.BookService;
import com.example.library.library.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class NavigationController {

    private UserService userService;
    private BookService bookService;

    public NavigationController(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
    }

    @RequestMapping(value = {"/", "/users"}, method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN')")
    public String users(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/user";
    }

    @GetMapping("/references")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String references() {
        return "references/references";
    }

    @GetMapping("/statistics")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String statistics() {
        return "statistics";
    }

    @GetMapping("/index")
    @PreAuthorize("hasAuthority('USER')")
    public String userBook(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "index";
    }
}
