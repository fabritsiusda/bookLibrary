package main.dao;

import main.models.Book;

import java.util.List;

public interface BookRepository {

    List<Book> getAllSortByTitle();
    List<Book> getAll();
    Book addBook(Book book);

}
