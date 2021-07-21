package com.example.library.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Calendar;
import java.util.Collections;

@Controller
@RequestMapping("/references")
public class AuthorController {

    @GetMapping("/authors")
    public String getAll(Model model) {
        return "references/author/author";
    }
}
