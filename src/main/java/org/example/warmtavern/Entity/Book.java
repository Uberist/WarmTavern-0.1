package org.example.warmtavern.Entity;

import lombok.Data;

import java.util.List;

@Data
public class Book {
    int id;
    String bookName;
    String bookCaption;
    String bookCoverPath;
    String bookPath;
    List<Author> authors;
    List<Genre> genres;
    List<Voice> voices;
    public Book(int id, String bookName,
                String bookCaption,
                String bookCoverPath,
                String bookPath) {
        this.id = id;
        this.bookName = bookName;
        this.bookCaption = bookCaption;
        this.bookCoverPath = bookCoverPath;
        this.bookPath = bookPath;
    }
    public Book() {}
}
