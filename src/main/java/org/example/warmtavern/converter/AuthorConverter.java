package org.example.warmtavern.converter;

import org.example.warmtavern.DAO.JdbcAuthorRepository;
import org.example.warmtavern.Entity.Author;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AuthorConverter implements Converter<String, Author> {
    JdbcAuthorRepository jdbcAuthorRepository;
    public AuthorConverter(JdbcAuthorRepository jdbcAuthorRepository) {
        this.jdbcAuthorRepository = jdbcAuthorRepository;
    }
    @Override
    public Author convert(String source) {
        return jdbcAuthorRepository.findById(Integer.parseInt(source)).get();
    }
}
