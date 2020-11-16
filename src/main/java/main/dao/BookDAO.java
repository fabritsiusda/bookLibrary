package main.dao;

import main.models.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookDAO implements BookRepository {

    private final RowMapper<Book> ROW_MAPPER = (rSet, i) ->
            new Book(rSet.getLong("id"),
                    rSet.getString("title"),
                    rSet.getString("author"),
                    rSet.getString("description"));

    private final JdbcTemplate jdbcTemplate;

    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> getAllSortByTitle() {
        return jdbcTemplate.query("select id, title, author, description from book order by title desc",ROW_MAPPER);
    }

    @Override
    public List<Book> getAll() {
        return jdbcTemplate.query("select id, title, author, description from book",ROW_MAPPER);
    }

    @Override
    public Book addBook(Book book) {
        int id = jdbcTemplate.update("insert into book (title, author, description) values(?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getDescription());
        return null;
    }
}
