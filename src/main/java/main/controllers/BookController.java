package main.controllers;

import main.models.Book;
import main.services.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAll(){
        return service.getAllBooksSortByTitle();
    }

    @GetMapping("/groupByAuthor")
    public ResponseEntity<?> getGroupByAuthor(){
        return service.getAllGroupByAuthors();
    }

    @PostMapping("/")
    public ResponseEntity<?> addBook(@RequestBody Book book){
        return service.addBook(book);
    }

}
