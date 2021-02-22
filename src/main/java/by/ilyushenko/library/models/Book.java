package by.ilyushenko.library.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private int count;

    private int countOfSheets;

    private Integer dateOfIssue;

    private String name;

    @Enumerated(EnumType.STRING)
    private Genre genre;
    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Integer getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(Integer dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public int getCountOfSheets() {
        return countOfSheets;
    }

    public void setCountOfSheets(int countOfSheets) {
        this.countOfSheets = countOfSheets;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", count=" + count +
                ", countOfSheets=" + countOfSheets +
                ", dateOfIssue=" + dateOfIssue +
                ", name='" + name + '\'' +
                ", genre=" + genre +
                ", author=" + author +
                '}';
    }
}
