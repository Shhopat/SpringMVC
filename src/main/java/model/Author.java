package model;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;

    public Author() {
    }

    public Author(String name, String surname, String lastName) {
        this.name = name;
        this.surname = surname;
        this.lastName = lastName;
    }

    @Column(name = "name", length = 30, nullable = false, unique = true)
    public String name;

    @Column(name = "surname", length = 30, nullable = false)
    public String surname;

    @Column(name = "last_name", length = 30, nullable = false)
    public String lastName;

    @OneToMany(mappedBy = "author")
    public List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return id + ". " + surname + " " + name + " " + lastName;
    }
}
