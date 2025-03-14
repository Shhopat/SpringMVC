package controller;

import dao.AuthorDao;
import model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorDao authorDao;

    @Autowired
    public AuthorController(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }


    @GetMapping
    public String getAllAuthor(Model model) {
        model.addAttribute("authors", authorDao.getAllAuthors());
        return "authors";
    }

    @GetMapping("/new")
    public String getForm(@ModelAttribute("author") Author author) {
        return "form";
    }


    @PostMapping
    public String createAuthor(@ModelAttribute("author") Author author) {
        authorDao.createAuthor(author);
        return "redirect:/authors";
    }

    @GetMapping("/{id}")
    public String getAuthor(@PathVariable("id") int id, Model model) {
        model.addAttribute("author", authorDao.getByAuthor(id));
        return "/authorIndex";
    }

    @GetMapping("{id}/edit")
    public String getFormEdit(@PathVariable("id") int id, Model model) {
        model.addAttribute("author", authorDao.getByAuthor(id));
        return "edit";

    }

    @PatchMapping("/{id}")
    public String edit(@PathVariable("id") int id, @ModelAttribute("author") Author author) {
        authorDao.update(id, author);
        return "redirect:/authors";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        authorDao.remove(id);
        return "redirect:/authors";
    }

}
