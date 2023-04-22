package fr.amurotakahashi.cefimtestcda2.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "notice")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "book_id", updatable = false, insertable = false)
    private Book book;

    @Column(name = "rating")
    private Integer rating;

    public Notice() {
    }

    public Notice(Book book, Integer rating) {
        this.book = book;
        this.rating = rating;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notice notice = (Notice) o;
        return Objects.equals(book, notice.book) && Objects.equals(rating, notice.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book, rating);
    }

    @Override
    public String toString() {
        return "Notice{" +
                "book=" + book +
                ", rating=" + rating +
                '}';
    }
}
