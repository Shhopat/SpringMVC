package controller;

import dao.AuthorDao;
import dao.BookDao;
import model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDao bookDao;
    private final AuthorDao authorDao;

    @Autowired
    public BookController(BookDao bookDao, AuthorDao authorDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
    }

    @GetMapping
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookDao.getBooks());
        return "books";
    }

    @GetMapping("/new")
    public String getForm(@ModelAttribute("book") Book book, Model model) {
        model.addAttribute("authors", authorDao.getAllAuthors());
        return "formBook";
    }

    @PostMapping
    public String create(@ModelAttribute("book") Book book) {
        bookDao.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String getBookById(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDao.getBookById(id));
        return "book";

    }
}
