package main.services;

import main.models.Book;
import main.repositories.BookRepository;
import main.responses.BookResponse;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<?> getAllBooksSortByTitle(){ ;
        List<Book> books = repository.findAll(Sort.by(Sort.Order.desc("title")));
        return  new ResponseEntity<>(new BookResponse(books), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> addBook(Book book) {
        if (book.getAuthor() == null || book.getAuthor().length() > 150 || book.getAuthor().isEmpty())
            return new ResponseEntity<>("Author with error", HttpStatus.BAD_REQUEST);
        if (book.getTitle() == null || book.getTitle().length() > 150 || book.getTitle().isEmpty())
            return new ResponseEntity<>("Title is empty", HttpStatus.BAD_REQUEST);
        if (book.getId() != null)
            return new ResponseEntity<>("id must be null", HttpStatus.BAD_REQUEST);
        book = repository.save(book);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    public ResponseEntity<?> getAllGroupByAuthors(){
        Map<String, List<Book>> map = new HashMap<>();
        repository.findAll().forEach(b -> {
            if (!map.containsKey(b.getAuthor()))
                map.put(b.getAuthor(), new ArrayList<>());
            map.get(b.getAuthor()).add(b);
        });
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
