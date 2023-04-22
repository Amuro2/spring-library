package fr.amurotakahashi.cefimtestcda2.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

import java.util.Objects;

@Entity
@Table(name = "book")
public class Book {
    public static final Book madameBovary = new Book("Madame Bovary", "", 12, "NEUF", null, null);
    public static final Book leMedecinMalgreLui = new Book("Le médecin malgré lui", "", 10, "", null, null);
    public static final Book nouveauLivreQuiNExistaitPas = new Book("Nouveau livre qui n'existait pas", "", -9999, "ABIME", null, null);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "pages_count")
    private Integer pagesCount;

    @Column(name = "state")
    private String state;

    @ManyToOne
    @JoinColumn(name = "author_id", updatable = false, insertable = false)
    private Author author;

    @ManyToOne
    @JoinColumn(name = "genre_id", updatable = false, insertable = false)
    private Genre genre;

    public Book() {

    }

    public Book(String title, String description, Integer pagesCount, String state, Author author) {
        this.title = title;
        this.description = description;
        this.pagesCount = pagesCount;
        this.state = state;
        this.author = author;
    }

    public Book(String title, String description, Integer pagesCount, String state, Author author, Genre genre) {
        this.title = title;
        this.description = description;
        this.pagesCount = pagesCount;
        this.state = state;
        this.author = author;
        this.genre = genre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(Integer pagesCount) {
        this.pagesCount = pagesCount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(title, book.title)
                && Objects.equals(description, book.description)
                && Objects.equals(pagesCount, book.pagesCount)
                && Objects.equals(state, book.state)
                && Objects.equals(author, book.author)
                && Objects.equals(genre, book.genre)
                ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title
                , description
                , pagesCount
                , state
                , author
                , genre
        );
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                '}';
    }
}
