package org.example.warmtavern.controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.warmtavern.DAO.JdbcBookRepository;
import org.example.warmtavern.DAOInterface.AuthorRepository;
import org.example.warmtavern.DAOInterface.GenreRepository;
import org.example.warmtavern.DAOInterface.VoiceRepository;
import org.example.warmtavern.Entity.Author;
import org.example.warmtavern.Entity.Book;
import org.example.warmtavern.Entity.Genre;
import org.example.warmtavern.Entity.Voice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/AdminPage")
public class AdminController {
    JdbcBookRepository jdbcBookRepository;
    GenreRepository genreRepository;
    AuthorRepository authorRepository;
    VoiceRepository voiceRepository;
//    @Value("${upload.pathArchiveBook}")
//    private String PathBookArchive;
//    @Value("${upload.pathArchiveAvatarBook}")
//    private String PathAvatarBook;
    @Autowired
    public AdminController(GenreRepository genreRepository,
                           AuthorRepository authorRepository,
                           VoiceRepository voiceRepository, JdbcBookRepository jdbcBookRepository) {
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
        this.voiceRepository = voiceRepository;
        this.jdbcBookRepository = jdbcBookRepository;
    }
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("genres", genreRepository.findAll());
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("voices", voiceRepository.findAll());
        model.addAttribute("book", new Book());
        model.addAttribute("genre", new Genre());
        model.addAttribute("author", new Author());
        model.addAttribute("voice", new Voice());
        model.addAttribute("books", this.jdbcBookRepository.findAll());
    }
    @GetMapping
    public String showAdminPage(){
        return "authPages/AdminPage";
    }
    @PostMapping
    public String processBook(
            Book book,
            @RequestParam("file-book") MultipartFile bookFileUpload,
            @RequestParam("file-avatar-book") MultipartFile avatarFileUpload) throws IOException {
        jdbcBookRepository.save(book, bookFileUpload, avatarFileUpload);
        return "redirect:/AdminPage";
    }
    @PostMapping(value = "/addGenre")
    public String processGenre(Genre genre){
        genreRepository.save(genre);
        log.info("Genre processed: " + genre);
        return "redirect:/AdminPage";
    }
    @PostMapping(value = "/addAuthor")
    public String processAuthor(Author author){
        authorRepository.save(author);
        log.info("Author processed: " + author);
        return "redirect:/AdminPage";
    }
    @PostMapping(value = "/addVoice")
    public String processVoice(Voice voice){
        voiceRepository.save(voice);
        log.info("Voice processed: " + voice);
        return "redirect:/AdminPage";
    }
    @PostMapping(value = "/deleteBook")
    public String deleteBook(int idBook){
        jdbcBookRepository.deleteById(idBook);
        return "redirect:/AdminPage";
    }
}
