package org.example.warmtavern.controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.warmtavern.DAO.JdbcAuthorRepository;
import org.example.warmtavern.DAO.JdbcBookRepository;
import org.example.warmtavern.DAO.JdbcGenreRepository;
import org.example.warmtavern.DAO.JdbcVoiceRepository;
import org.example.warmtavern.Entity.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/SearchBook")
public class SearchBookController {
    private final JdbcAuthorRepository jdbcAuthorRepository;
    private final JdbcBookRepository jdbcBookRepository;
    private JdbcBookRepository bookRepository;
    private JdbcAuthorRepository authorRepository;
    private JdbcVoiceRepository voiceRepository;
    private JdbcGenreRepository JdbcGenreRepository;
    public SearchBookController(JdbcBookRepository bookRepository, JdbcAuthorRepository authorRepository,
                                JdbcVoiceRepository voiceRepository, JdbcGenreRepository JdbcGenreRepository, JdbcAuthorRepository jdbcAuthorRepository, JdbcBookRepository jdbcBookRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.voiceRepository = voiceRepository;
        this.JdbcGenreRepository = JdbcGenreRepository;
        this.jdbcAuthorRepository = jdbcAuthorRepository;
        this.jdbcBookRepository = jdbcBookRepository;
    }
    @ModelAttribute
    public void addAttributes(Model model){
        model.addAttribute("voices", voiceRepository.findAll());
        model.addAttribute("genres", JdbcGenreRepository.findAll());
        model.addAttribute("authors", jdbcAuthorRepository.findAll());
        model.addAttribute("book", new Book());
    }
    @GetMapping
    public String showSearchBook(Model model) {
        return "bookPages/SearchBookPage";
    }
    @PostMapping
    public String searchBook(Book book, RedirectAttributes redirectAttributes){
        List<Book> resultBooks = bookRepository.searchBook(book);
        redirectAttributes.addFlashAttribute("books", resultBooks);
        return "redirect:/SearchBook";
    }
}
