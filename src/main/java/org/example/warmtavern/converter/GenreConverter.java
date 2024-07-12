package org.example.warmtavern.converter;

import org.example.warmtavern.DAOInterface.GenreRepository;
import org.example.warmtavern.Entity.Genre;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GenreConverter implements Converter<String, Genre> {
    GenreRepository genreRepository;
    public GenreConverter(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }
    @Override
    public Genre convert(String source) {
        return genreRepository.findById(Integer.parseInt(source)).get();
    }
}
