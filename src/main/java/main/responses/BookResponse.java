package main.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import main.models.Book;

import java.util.List;

@Data
@AllArgsConstructor
public class BookResponse {

    private List<Book> books;

}
