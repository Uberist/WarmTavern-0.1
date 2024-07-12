package org.example.warmtavern.DAO;

import org.example.warmtavern.DAOInterface.AuthorRepository;
import org.example.warmtavern.Entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcAuthorRepository implements AuthorRepository {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Author> findById(int id) {
        List<Author> result = jdbcTemplate.query(
                "select * from authors where id = ?",
                this::mapRowToAuthor,
                id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public List<Author> findByBook(int bookId) {
        List<Integer> bookIdArray = jdbcTemplate.query(
                "select * from books_author where book_id = ?",
                this::mapRowToBooksId,
                bookId
        );
        List<Author> authors = new ArrayList<>();
        for(Integer id : bookIdArray){
            authors.add(this.findById(id).get());
            if(authors.size() == bookIdArray.size())
                return authors;
        }
        return authors;
    }

    @Override
    public Iterable<Author> findAll() {
        return jdbcTemplate.query(
                "select * from authors",
                this::mapRowToAuthor
        );
    }
//Закончил тут 02.27 метод который ищет автора по имени,
// не смог разделить имя на подстроки.
// Ушел переделывать adminPage чтобы значением передавал id автора а не фио
    @Override
    public Optional<Author> findByName(String name) {
        //Author res = jdbcTemplate.query(
        //        "select * from authors where author_first_name = 'strinven' and author_last_name = 'h' and author_middle_name = 'king'",
        //        this::mapRowToAuthor,
        //
        //);
        return null;
    }

    @Override
    public Author save(Author author) {
        jdbcTemplate.update(
                "insert into authors (author_first_name, author_last_name, author_middle_name) values (?, ?, ?)",
                author.getAuthor_first_name(),
                author.getAuthor_last_name(),
                author.getAuthor_middle_name()
        );
        return author;
    }

    private Integer mapRowToBooksId(ResultSet rs, int rowNum) throws SQLException {
           return rs.getInt("author_id");
    }
    private Author mapRowToAuthor(ResultSet row, int rowNum) throws SQLException {
        return new Author(
                Integer.parseInt(row.getString("id")),
                row.getString("author_first_name"),
                row.getString("author_last_name"),
                row.getString("author_middle_name")
        );
    }
}
