package fr.amurotakahashi.cefimtestcda2.repositories;

import fr.amurotakahashi.cefimtestcda2.entities.Book;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class BookRepositoryTests {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testFindAll() {
        List<Book> books = bookRepository.findAll();

        System.out.println(books);

        assert books != null;

    }

    @Test
    void testFindById() {
        Optional<Book> optionalBook = bookRepository.findById(1);

        System.out.println(optionalBook.orElse(null));
    }

    @Test
    void testFindByTitleContaining() {
        List<Book> books = bookRepository.findByTitleLike("M");

        System.out.println(books);

        for(Book book : books) {
            assert book.getTitle().contains("M");
        }
    }

    @Test
    void testFindByTitleContainingIgnoreCase() {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase("m");

        System.out.println(books);

        for(Book book : books) {
            assert book.getTitle().toUpperCase().contains("M");
        }
    }

    @Test
    void testSave() {
        Book book = new Book("Ababaffieegzeg", "", 12, "NEUF", null);

        bookRepository.save(book);
        bookRepository.save(book);

        System.out.println(bookRepository.findAll());
    }

}
