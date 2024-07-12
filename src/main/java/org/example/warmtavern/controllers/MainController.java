package org.example.warmtavern.controllers;

import ch.qos.logback.core.model.Model;
import lombok.extern.slf4j.Slf4j;
import org.example.warmtavern.DAO.JdbcAuthorRepository;
import org.example.warmtavern.DAO.JdbcBookRepository;
import org.example.warmtavern.Entity.Author;
import org.example.warmtavern.Entity.Book;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/MainPage")
public class MainController {
    JdbcBookRepository JdbcBookRepository;
    JdbcAuthorRepository JdbcAuthorRepository;
    public MainController(JdbcBookRepository JdbcBookRepository, JdbcAuthorRepository JdbcAuthorRepository) {
        this.JdbcBookRepository = JdbcBookRepository;
        this.JdbcAuthorRepository = JdbcAuthorRepository;
    }
    @ModelAttribute
    public void addAttributes(org.springframework.ui.Model model) {
        Iterable<Book> books = JdbcBookRepository.findAllWithLimit(5);
        model.addAttribute("books", books);
        log.info(books.iterator().next().getAuthors().get(0).getAuthor_first_name() + "--------------> я здесь");
//        List<Author> authors = new ArrayList<>();
//        for (Book book : books) {
//            authors.add()
//        }
    }
    @GetMapping
    public String showMainPage(Model model){
        return "MainPage";
    }
}
