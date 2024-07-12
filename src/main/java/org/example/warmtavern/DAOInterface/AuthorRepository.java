package org.example.warmtavern.DAOInterface;

import org.example.warmtavern.Entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    public Optional<Author> findById(int id);
    public List<Author> findByBook(int bookId);
    public Iterable<Author> findAll();
    public Optional<Author> findByName(String name);
    public Author save(Author author);
}
