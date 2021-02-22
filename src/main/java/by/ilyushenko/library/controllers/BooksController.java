package by.ilyushenko.library.controllers;

import by.ilyushenko.library.models.Author;
import by.ilyushenko.library.models.Book;
import by.ilyushenko.library.models.Genre;
import by.ilyushenko.library.repository.BookRepository;
import by.ilyushenko.library.service.AuthorService;
import by.ilyushenko.library.service.BookService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/books")
public class BooksController {
    private static final int PAGE_SIZE = 5;
    private static final int PAGE_ONE = 1;
    private final BookService bookService;
    private final AuthorService authorService;
    private List<Author> authors;

    public BooksController( BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/getAll")
    public String showBooks(Model model) {
        Pageable firstPageWithFiveElements = PageRequest.of(PAGE_ONE - 1, PAGE_SIZE);
        Page<Book> bookPage = bookService.findPaginated(firstPageWithFiveElements);
        model.addAttribute("bookPage", bookPage);
        authors = authorService.getAll();
        model.addAttribute("authors", authors);
        addTotalPages(model, bookPage.getTotalPages());
        return "book/books";
    }

    @GetMapping("/getOne")
    @ResponseBody
    public Optional<Book> getOne(Integer id) {
        return bookService.findById(id);
    }

    @PostMapping("/addNew")
    public String addNew(Book book) {
        bookService.addNew(book);
        return "redirect:/books/getAll";
    }

    @RequestMapping(value = "/editBook", method = {RequestMethod.PUT, RequestMethod.GET})
    public String editBook(Book book) {
        bookService.update(book);
        return "redirect:/books/getAll";
    }


    @GetMapping("/pagination")
    public String showBooksPagination(@RequestParam("page") Optional<Integer> page,
                                      @RequestParam("size") Optional<Integer> size,
                                      Model model) {
        Pageable firstPageWithFiveElements = getPageable(page, size);
        Page<Book> bookPage = bookService.findPaginated(firstPageWithFiveElements);
        model.addAttribute("bookPage", bookPage);
        model.addAttribute("authors", authors);
        addTotalPages(model, bookPage.getTotalPages());
        return "book/books::book_list";
    }

    @PostMapping("/search")
    public String showBooksByName(@RequestParam(required = false) String search,
                                  @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size,
                                  Model model) {
        Pageable firstPageWithFiveElements = getPageable(page, size);
        int totalPages;
        Page<Book> bookPage;
        if (StringUtils.isBlank(search)) {
            bookPage = bookService.findPaginated(firstPageWithFiveElements);
        } else {
            bookPage = bookService.findByNameContainingIgnoreCase(search, firstPageWithFiveElements);
        }
        model.addAttribute("bookPage", bookPage);
        model.addAttribute("authors", authors);
        totalPages = bookPage.getTotalPages();
        addTotalPages(model, totalPages);
        return "book/books::book_list";
    }

    private void addTotalPages(Model model, int totalPages) {
        if (totalPages > 1) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
    }

    private Pageable getPageable(Optional<Integer> page, Optional<Integer> size) {
        int currentPage = page.orElse(PAGE_ONE);
        int pageSize = size.orElse(PAGE_SIZE);
        return PageRequest.of(currentPage - 1, pageSize);
    }
}
