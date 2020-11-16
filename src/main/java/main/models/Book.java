package main.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", length = 150, nullable = false)
    private String title;
    @Column(name = "author", length = 150, nullable = false)
    private String author;
    @Column(name = "description", length = 150, nullable = true)
    private String description;

}
