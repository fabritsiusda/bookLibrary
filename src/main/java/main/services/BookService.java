package main.services;

import main.dao.BookDAO;
import main.models.Book;
import main.responses.BookResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookService {

    private final BookDAO repository;

    public BookService(BookDAO repository) {
        this.repository = repository;
    }

    public ResponseEntity<?> getAllBooksSortByTitle() {
        return new ResponseEntity<>(new BookResponse(repository.getAllSortByTitle()), HttpStatus.OK);
    }

    public ResponseEntity<?> getAllGroupByAuthors() {
        Map<String, List<Book>> map = new HashMap<>();
        repository.getAll().forEach(b -> {
            if (!map.containsKey(b.getAuthor()))
                map.put(b.getAuthor(), new ArrayList<>());
            map.get(b.getAuthor()).add(b);
        });
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    public ResponseEntity<?> addBook(Book book) {
        if (book.getAuthor() == null || book.getAuthor().length() > 150 || book.getAuthor().isEmpty())
            return new ResponseEntity<>("Author with error", HttpStatus.BAD_REQUEST);
        if (book.getTitle() == null || book.getTitle().length() > 150 || book.getTitle().isEmpty())
            return new ResponseEntity<>("Title is empty", HttpStatus.BAD_REQUEST);
        if (book.getId() != null)
            return new ResponseEntity<>("id must be null", HttpStatus.BAD_REQUEST);
        book = repository.addBook(book);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
}
