package main.dao;

import main.models.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.Statement;
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
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            String sql = "insert into book (title, author, description) values(?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, book.getTitle());
            st.setString(2, book.getAuthor());
            st.setString(3, book.getDescription());
            return st;
        }, holder);
        long id = holder.getKey().longValue();
        book.setId(id);
        return book;
    }
}
