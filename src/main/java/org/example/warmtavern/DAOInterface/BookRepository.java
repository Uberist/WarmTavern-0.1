package org.example.warmtavern.DAOInterface;

import org.example.warmtavern.Entity.Book;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Iterable<Book> findAll();
    Iterable<Book> findAllWithLimit(int limit);
    Optional<Book> findById(long id);
    Book save(Book book, MultipartFile bookFile, MultipartFile bookAvatarFile) throws IOException;
    Optional<Book> findByCaption(String caption);
    Book update(Book book);
    boolean deleteById(int id);
    List<Book> searchBook(Book book);
}
