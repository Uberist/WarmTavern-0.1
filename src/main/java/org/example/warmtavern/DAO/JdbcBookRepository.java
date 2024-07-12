package org.example.warmtavern.DAO;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.example.warmtavern.DAOInterface.AuthorRepository;
import org.example.warmtavern.DAOInterface.BookRepository;
import org.example.warmtavern.DAOInterface.GenreRepository;
import org.example.warmtavern.Entity.Author;
import org.example.warmtavern.Entity.Book;
import org.example.warmtavern.Entity.Genre;
import org.example.warmtavern.Entity.Voice;
import org.example.warmtavern.converter.GenreConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.handler.AbstractUrlHandlerMapping;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class JdbcBookRepository implements BookRepository {
    private final GenreConverter genreConverter;
    @Value("${upload.pathArchiveBook}")
    private String PathBookArchive;
    @Value("${upload.pathArchiveAvatarBook}")
    private String PathAvatarBook;
    private JdbcTemplate jdbcTemplate;
    private AuthorRepository authorRepository;
    private JdbcGenreRepository jdbcGenreRepository;
    private JdbcVoiceRepository jdbcVoiceRepository;
    private JdbcAuthorRepository jdbcAuthorRepository;
    @Autowired
    public JdbcBookRepository(JdbcTemplate jdbcTemplate, AuthorRepository authorRepository, JdbcGenreRepository jdbcGenreRepository, JdbcVoiceRepository jdbcVoiceRepository, JdbcAuthorRepository jdbcAuthorRepository, GenreConverter genreConverter) {
        this.jdbcTemplate = jdbcTemplate;
        this.authorRepository = authorRepository;
        this.jdbcGenreRepository = jdbcGenreRepository;
        this.jdbcVoiceRepository = jdbcVoiceRepository;
        this.jdbcAuthorRepository = jdbcAuthorRepository;
        this.genreConverter = genreConverter;
    }
    @Override
    public Iterable<Book> findAll() {
        List<Book> books = new ArrayList<>();
        books = jdbcTemplate.query(
                "select * from books",
                this::mapRowToBook
        );
        for(Book book : books)
            book.setAuthors(authorRepository.findByBook(book.getId()));
        for(Book book : books)
            book.setGenres(jdbcGenreRepository.findByBook(book.getId()));
        for(Book book : books)
            book.setVoices(jdbcVoiceRepository.findByBook(book.getId()));
        return books;
    }
    @Override
    public Iterable<Book> findAllWithLimit(int limit) {
        List<Book> books = jdbcTemplate.query(
                "select * from books order by id desc limit ?",
                this::mapRowToBook,
                limit
        );
        for(Book book : books)
            book.setAuthors(authorRepository.findByBook(book.getId()));
        for(Book book : books)
            book.setGenres(jdbcGenreRepository.findByBook(book.getId()));
        for(Book book : books)
            book.setVoices(jdbcVoiceRepository.findByBook(book.getId()));
        return books;
    }
    @Override
    public Optional<Book> findById(long id) {
        List<Book> results = jdbcTemplate.query(
                "select id, book_name, book_caption, book_cover_path, book_path from books where id=?",
                this::mapRowToBook,
                id
        );

        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Book save(Book book, MultipartFile bookFile, MultipartFile avatarFile) throws IOException {
        jdbcTemplate.update(
                "insert into books (book_name, book_caption) values(?, ?)",
                book.getBookName(),
                book.getBookCaption()
        );
        Book newBook = this.findByCaption(book.getBookCaption()).get();
        File bookFileIo = new File(PathBookArchive, newBook.getId() + ".mp3");
        File avatarFileIo = new File(PathAvatarBook, newBook.getId() + ".jpg");
        bookFileIo.createNewFile();
        avatarFileIo.createNewFile();
        bookFile.transferTo(bookFileIo);
        avatarFile.transferTo(avatarFileIo);
        newBook.setBookPath("./images/archiveBook/" + bookFileIo.getName());
        newBook.setBookCoverPath("./images/" + avatarFileIo.getName());
        this.update(newBook);
        if(book.getAuthors() != null)
            for (Author author : book.getAuthors()){
                jdbcTemplate.update(
                        "insert into books_author (book_id, author_id) values ( ?, ? )",
                        newBook.getId(),
                        author.getId()
                );
            }
        if(book.getVoices() != null)
            for (Voice voice : book.getVoices()){
                jdbcTemplate.update(
                        "insert into book_voice (book_id, voice_id) values ( ?, ? )",
                        newBook.getId(),
                        voice.getId()
                );
            }
        if(book.getGenres() != null)
            for(Genre genre : book.getGenres()){
                jdbcTemplate.update(
                        "insert into books_genre (book_id, genre_id) values( ?, ?)",
                        newBook.getId(),
                        genre.getId()
                );
            }
        return book;
    }

    @Override
    public Optional<Book> findByCaption(String caption) {
        List<Book> book = jdbcTemplate.query(
                "select * from books where book_caption = ?",
                this::mapRowToBook,
                caption
        );
        return book.isEmpty() ? Optional.empty() : Optional.of(book.get(0));
    }

    @Override
    public Book update(Book book) {
        jdbcTemplate.update(
                "update books set book_name = ?, book_caption = ?, book_cover_path = ?, book_path = ? where id = ?",
                    book.getBookName(),
                    book.getBookCaption(),
                    book.getBookCoverPath(),
                    book.getBookPath(),
                    book.getId()
        );
        return book;
    }
    @Override
    public boolean deleteById(int id){
        Book book = this.findById(id).get();
        List<Author> authors = jdbcAuthorRepository.findByBook(book.getId());
        List<Voice> voices = jdbcVoiceRepository.findByBook(book.getId());
        List<Genre> genres = jdbcGenreRepository.findByBook(book.getId());
        if(authors != null)
            for(Author author : authors){
                jdbcTemplate.update(
                        "delete from books_author where book_id = ? and author_id = ?",
                        book.getId(),
                        author.getId()
                );
            }
        if(voices != null)
            for(Voice voice : voices){
                jdbcTemplate.update(
                        "delete from book_voice where book_id = ? and voice_id = ?",
                        book.getId(),
                        voice.getId()
                );
            }
        if(genres != null)
            for(Genre genre : genres){
                jdbcTemplate.update(
                        "delete from books_genre where book_id = ? and genre_id = ?",
                        book.getId(),
                        genre.getId()
                );
            }
        jdbcTemplate.update(
                "delete from books where id = ?",
                id
        );
        return true;
    }

    @Override
    public List<Book> searchBook(Book book) {
        List<Book> resultList = new ArrayList<>();
        List<Book> newBook = jdbcTemplate.query(
                "select * from books where book_caption like ? and book_name like ?",
                this::mapRowToBook,
                "%" + book.getBookCaption() + "%",
                "%" + book.getBookName() + "%"
        );
        log.info(book + "--> первичный полученый объект из формы");
        System.out.println();
        System.out.println();
        log.info(newBook + "--> результат по первому запросу (" + newBook.size() + ")");
        System.out.println();
        System.out.println();
        if(!newBook.isEmpty()){
            for(Book bookItem : newBook){
                bookItem.setAuthors(jdbcAuthorRepository.findByBook(bookItem.getId()));
                bookItem.setGenres(jdbcGenreRepository.findByBook(bookItem.getId()));
                bookItem.setVoices(jdbcVoiceRepository.findByBook(bookItem.getId()));
            }
            log.info(newBook + "--> список объектов после первой выборке полсе обрботки");
            System.out.println();
            System.out.println();
            List<Book> resultSelectBookForAuthor = new ArrayList<>();
            List<Book> resultSelectBookForGenre = new ArrayList<>();
            List<Book> resultSelectBookForVoice = new ArrayList<>();
            if(book.getAuthors() == null)
                for(Book book1 : newBook)
                    resultSelectBookForAuthor.add(book1);
            if(book.getVoices() == null)
                for(Book book1 : newBook)
                    resultSelectBookForVoice.add(book1);
            if(book.getGenres() == null)
                for(Book book1 : newBook)
                    resultSelectBookForGenre.add(book1);
            for(Book bookItem : newBook){
                List<Author> authors = bookItem.getAuthors();
                List<Author> authorsFromForm = book.getAuthors();
                List<Genre> genres = bookItem.getGenres();
                List<Genre> genresFromForm = book.getGenres();
                List<Voice> voices = bookItem.getVoices();
                List<Voice> voicesFromForm = book.getVoices();
                if(authors != null && authorsFromForm != null)
                    for(Author author : authors)
                        for(Author authorFromForm : authorsFromForm)
                            if(author.getId() == authorFromForm.getId())
                                resultSelectBookForAuthor.add(bookItem);
                if(genres != null && genresFromForm != null)
                    for(Genre genre : genres)
                        for(Genre genreFromForm : genresFromForm)
                            if(genre.getId() == genreFromForm.getId())
                                resultSelectBookForGenre.add(bookItem);
                if(voices != null && voicesFromForm != null)
                    for(Voice voice : voices)
                        for(Voice voiceFromForm : voicesFromForm)
                            if(voice.getId() == voiceFromForm.getId())
                                resultSelectBookForVoice.add(bookItem);
            }
            List<Book> oneStepResultList = new ArrayList<>();
            for(Book bookItem : resultSelectBookForAuthor)
                for(Book bookItemTwo : resultSelectBookForVoice)
                    if(bookItem.getId() == bookItemTwo.getId())
                        oneStepResultList.add(bookItem);
            for(Book bookItem : resultSelectBookForGenre)
                for(Book bookItemTwo : oneStepResultList)
                    if(bookItem.getId() == bookItemTwo.getId())
                        resultList.add(bookItem);
            log.info(resultSelectBookForAuthor + "--> результат проверки по авторам");
            System.out.println();
            System.out.println();
            log.info(resultSelectBookForGenre + "--> результат проверки по жанрам");
            System.out.println();
            System.out.println();
            log.info(resultSelectBookForVoice + "--> результат проверки по чтецам");
            System.out.println();
            System.out.println();
            log.info(resultList + "--> итоговая выборка");
        }
        return resultList;
    }

    private Book mapRowToBook(ResultSet row, int rowNum) throws SQLException {
        return new Book(
                Integer.parseInt(row.getString("id")),
                row.getString("book_name"),
                row.getString("book_caption"),
                row.getString("book_cover_path"),
                row.getString("book_path")
        );
    }
}
