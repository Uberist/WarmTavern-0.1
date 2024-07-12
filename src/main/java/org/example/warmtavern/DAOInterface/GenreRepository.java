package org.example.warmtavern.DAOInterface;

import org.example.warmtavern.Entity.Genre;

import java.util.Optional;

public interface GenreRepository {
    public Iterable<Genre> findAll();
    public Optional<Genre> findById(int id);
    public Iterable<Genre> findByBook(int bookId);
    public Genre save(Genre genre);
}
