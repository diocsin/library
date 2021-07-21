package com.example.library.library.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.Year;
import java.util.Date;
import java.util.Objects;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;

    @Column(columnDefinition = "int(8) not null default '0'")
    private Integer generalCount;

    @Column(columnDefinition = "int(8) not null default '0'")
    private Integer availableCount;

    @Column(columnDefinition = "int(6) not null default '0'")
    private Integer countOfSheets;

    @DateTimeFormat
    private Date yearOfIssue;

    @Column(length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private Genre genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "pid")
    private Author author;

    @Lob
    private byte[] cover;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
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

    public Integer getGeneralCount() {
        return generalCount;
    }

    public Integer getAvailableCount() {
        return availableCount;
    }

    public byte[] getCover() {
        return cover;
    }

    public void setGeneralCount(Integer generalCount) {
        this.generalCount = generalCount;
    }

    public void setAvailableCount(Integer availableCount) {
        this.availableCount = availableCount;
    }

    public Date getYearOfIssue() {
        return yearOfIssue;
    }

    public void setYearOfIssue(Date yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
    }

    public Integer getCountOfSheets() {
        return countOfSheets;
    }

    public void setCountOfSheets(Integer countOfSheets) {
        this.countOfSheets = countOfSheets;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return pid.equals(book.pid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pid);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + pid +
                ", name='" + name + '\'' +
                ", genre=" + genre +
                ", author=" + author +
                '}';
    }

}
