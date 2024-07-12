package org.example.warmtavern.Entity;

import lombok.Data;

@Data
public class Genre {
    int id;
    String Name;

    public Genre(int id, String name) {
        this.id = id;
        Name = name;
    }
    public Genre() {

    }
}
