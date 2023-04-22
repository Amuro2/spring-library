package fr.amurotakahashi.cefimtestcda2.entities.plus;

import fr.amurotakahashi.cefimtestcda2.entities.Author;
import fr.amurotakahashi.cefimtestcda2.entities.Book;

import java.util.List;

public class AuthorWithBooks {
    private Author author;
    private List<Book> books;

    public AuthorWithBooks() {
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
