package org.example.warmtavern.DAO;

import org.example.warmtavern.DAOInterface.GenreRepository;
import org.example.warmtavern.Entity.Book;
import org.example.warmtavern.Entity.Genre;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Repository
public class JdbcGenreRepository implements GenreRepository {

    private JdbcTemplate jdbcTemplate;
    public JdbcGenreRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Iterable<Genre> findAll() {
        return jdbcTemplate.query(
                "select * from genre",
                this::mapRowToGenre);
    }

    @Override
    public Optional<Genre> findById(int id) {
        List<Genre> result = jdbcTemplate.query(
                "select * from genre where id = ?",
                this::mapRowToGenre,
                id
        );
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public List<Genre> findByBook(int bookId) {
        List<Integer> genresIds = jdbcTemplate.query(
                "select * from books_genre where book_id = ?",
                this::mapRotToGenreId,
                bookId
        );
        List<Genre> genres = new ArrayList<Genre>();
        for(Integer genreId : genresIds) {
            genres.add(this.findById(genreId).get());
        }
        return genres;
    }

    @Override
    public Genre save(Genre genre) {
        jdbcTemplate.update(
                "insert into genre (genre_name) values (?)",
                genre.getName()
        );
        return genre;
    }
    private Integer mapRotToGenreId(ResultSet rs, int rowNum) throws SQLException{
        return rs.getInt("genre_id");
    }
    private Genre mapRowToGenre(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(
            rs.getInt("id"),
            rs.getString("genre_name")
        );
    }
}
