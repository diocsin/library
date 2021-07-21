package com.example.library.library.controller;

import com.example.library.library.utils.AuthUtil;
import com.example.library.library.model.Book;
import com.example.library.library.service.AuthorService;
import com.example.library.library.service.BookService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/references/books")
public class BookController {

    private static final String BOOK_TABLE = "references/book/book :: book_list";
    private static final String ERROR_ALERT = "fragments/alert :: alert";
    private static final String EDIT_MODAL = "references/book/modal/editBook";
    private static final String ADD_MODAL = "references/book/modal/addBook";

    BookService bookService;

    AuthorService authorService;


    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping()
    public String getAll(Model model) {
        User user = (User) AuthUtil.getUserFromContext();
        Boolean isAdmin = user.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ADMIN"));
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "references/book/book";
    }

    @GetMapping("/addBook")
    public String addUser(Model model) {
        try {
            model.addAttribute("authors", authorService.getAllAuthors());
            model.addAttribute("book", new Book());
            return ADD_MODAL;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ADD_MODAL;
        }
    }

    @PostMapping("/saveBook")
    public String saveBook(Book book, Model model) {
        try {
            bookService.createNewBook(book);
            model.addAttribute("books", bookService.getAllBooks());
            model.addAttribute("message", "Книга успешно добавлена");
            model.addAttribute("alertClass", "alert-success");
            return BOOK_TABLE;
        } catch (Exception e) {
            model.addAttribute("users", bookService.getAllBooks());
            model.addAttribute("message", "Ошибка добавления книги");
            model.addAttribute("alertClass", "alert-danger");
            return BOOK_TABLE;
        }
    }

    @GetMapping("/edit")
    public String editBook(Long pid, Model model) {
        try {
            Book book = bookService.getBookById(pid);
            model.addAttribute("book", book);
            return EDIT_MODAL;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return EDIT_MODAL;
        }
    }

    @PostMapping("/updateBook")
    public String updateUser(Book book, Model model) {
        try {
            bookService.updateBook(book);
            model.addAttribute("books", bookService.getAllBooks());
            model.addAttribute("message", "Книга успешно изменена");
            model.addAttribute("alertClass", "alert-success");
            return BOOK_TABLE;
        } catch (Exception e) {
            model.addAttribute("books", bookService.getAllBooks());
            model.addAttribute("message", "Ошибка редактирования книги");
            model.addAttribute("alertClass", "alert-danger");
            return BOOK_TABLE;
        }
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteBook(Long pid, Model model) {
        try {
            bookService.deleteBookById(pid);
            model.addAttribute("books", bookService.getAllBooks());
            model.addAttribute("message", "Книга успешно удалена");
            model.addAttribute("alertClass", "alert-success");
            return BOOK_TABLE;
        } catch (Exception e) {
            model.addAttribute("books", bookService.getAllBooks());
            model.addAttribute("message", "Ошибка удаления книги");
            model.addAttribute("alertClass", "alert-danger");
            return BOOK_TABLE;
        }
    }

    @GetMapping("/filter")
    public String filterBook(String filterText, Model model) {
        List<Book> filterBooks;
        try {
            if (!StringUtils.isBlank(filterText)) {
                filterBooks = bookService.filterBook(filterText);
            } else {
                filterBooks = bookService.getAllBooks();
            }
            model.addAttribute("books", filterBooks);
            return BOOK_TABLE;
        } catch (Exception e) {
            model.addAttribute("books", bookService.getAllBooks());
            model.addAttribute("message", "При работе с книгами произошла ошибка");
            model.addAttribute("alertClass", "alert-danger");
            return BOOK_TABLE;
        }
    }


}
