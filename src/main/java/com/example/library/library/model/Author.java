package com.example.library.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(length = 50)
    private String secondName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Book> books;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
    @JsonIgnore
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String getFullFio() {
        return String.format("%s %s %s", lastName, firstName, secondName);
    }

    public String getFio() {
        if (secondName == null) {
            return String.format("%s %s.", lastName, firstName.charAt(0));
        }
        return String.format("%s %s. %s.", lastName, firstName.charAt(0), secondName.charAt(0));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;
        Author author = (Author) o;
        return pid.equals(author.pid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pid);
    }

    @Override
    public String toString() {
        return "Author{" +
                "pid=" + pid +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", surName='" + lastName + '\'' +
                '}';
    }
}
