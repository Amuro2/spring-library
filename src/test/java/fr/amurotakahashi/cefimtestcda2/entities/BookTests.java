package fr.amurotakahashi.cefimtestcda2.entities;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

@SpringBootTest
public class BookTests {

    @Test
    void testNewBook() {
        Book book = new Book("Madame Bovary", "", 12, "NEUF", null);

        System.out.println(book);
    }

    @Test
    void testEquals() {
        Book book1 = new Book("Madame Bovary", "", 12, "NEUF", null);
        Book book2 = new Book("Madame Bovary", "", 12, "NEUF", null);
        Book book3 = new Book("Le médecin malgré lui", "", 12, "ABIME", null);
        Book bookNull = null;

        assert book1.equals(book2);
        assert !book1.equals(book3);
        assert !Objects.equals(book1, bookNull);
    }

    @Test
    void testValidated() {
        boolean test = false;

        try {
            Book book = new Book("", "", -454, "", null);

            System.out.println(book.getPagesCount());
        } catch(Exception e) {
            test = true;
        }

        assert test;
    }

}
