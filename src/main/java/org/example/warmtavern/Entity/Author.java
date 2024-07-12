package org.example.warmtavern.Entity;

import lombok.Data;

@Data
public class Author {
    int id;
    String author_first_name;
    String author_last_name;
    String author_middle_name;

    public Author(int id, String author_first_name, String author_last_name, String author_middle_name) {
        this.id = id;
        this.author_first_name = author_first_name;
        this.author_last_name = author_last_name;
        this.author_middle_name = author_middle_name;
    }
    public Author() {}
}
