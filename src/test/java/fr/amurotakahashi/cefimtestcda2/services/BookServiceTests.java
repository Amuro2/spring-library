package fr.amurotakahashi.cefimtestcda2.services;

import fr.amurotakahashi.cefimtestcda2.entities.Book;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.InvalidParameterException;
import java.util.List;

@SpringBootTest
@Transactional
public class BookServiceTests {
    @Autowired
    private BookService bookService;

    @Test
    void testHelloBook() {
        assert bookService.helloBook().equals("Hello Book !!!");
    }

    @Test
    void testGetAllBooks() {
        System.out.println(bookService.getAllBooks());

        assert bookService.getAllBooks() != null;
    }

    @Test
    void testGetBookById() {
        int id = 1;
        Book book = bookService.getBookById(id);

        assert book != null;
        assert book.getId().equals(id);
    }

    @Test
    void testGetBookByIdNotExists() {
        int id = -9999;

        try {
            bookService.getBookById(id);

            assert false;
        } catch(EntityNotFoundException e) {
            assert true;
        }
    }

    @Test
    void testGetBooksByTitle() {
        List<Book> allBooks = bookService.getAllBooks();
        List<Book> books = bookService.getBooksByTitle("M");
        List<Book> booksCaseInsensitive = bookService.getBooksByTitleCaseInsensitive("M");

        System.out.println(allBooks);
        System.out.println(books);
        System.out.println(booksCaseInsensitive);

//        assert bookService.getBooksByTitle("M").contains(Book.madameBovary);
//        assert !bookService.getBooksByTitle("M").contains(Book.leMedecinMalgreLui);
    }

    @Test
    void testPostBookNotExists() {
        Book book = Book.nouveauLivreQuiNExistaitPas;

        long booksCount = bookService.countBooks();

        bookService.postBook(book);

        assert bookService.countBooks() == booksCount + 1;

        assert bookService.getAllBooks().contains(book);
        assert bookService.getAllBooks().contains(new Book("Nouveau livre qui n'existait pas", "", -9999, "ABIME", null));
    }

    @Test
    void testPostBookAlreadyExists() {
        long booksCount = bookService.countBooks();

        Book book = bookService.getBookById(1);

        try {
            bookService.postBook(book);

            assert false;
        } catch(EntityExistsException e) {
            assert true;
        }

        assert bookService.countBooks() == booksCount;
    }

    @Test
    void testPostBookBadRequest() {
        try {
            bookService.postBook(null);

            assert false;
        } catch(InvalidParameterException e) {
            assert true;
        }
    }

    @Test
    void testPatchBookExists() {
        int id = 1;

        Book book = bookService.getBookById(id);
        int pagesCount = book.getPagesCount();

        bookService.patchBookPages(id, pagesCount + 1);

        assert bookService.getBookById(id).getPagesCount().equals(pagesCount + 1);
    }

    @Test
    void testPatchBookNotExists() {
        int id = -9999;
        long booksCount = bookService.countBooks();

        Book book = bookService.patchBookPages(id, 9876);

        assert book == null;
        assert bookService.countBooks() == booksCount;
    }

    @Test
    void testPutBookExists() {
        int id = 1;

        Book updatedBook = new Book("Ce titre-là est beaucoup mieux", "", 12, "NEUF", null);

        long booksCount = bookService.countBooks();

        bookService.putBook(id, updatedBook);

        System.out.println(bookService.getAllBooks());

        assert bookService.countBooks() == booksCount;
        assert bookService.getBookById(id).equals(updatedBook);
    }

    @Test
    void testPutBookNotExists() {
        int id = -9999;

        Book updatedBook = new Book("Ce titre-là est beaucoup mieux", "", 12, "NEUF", null);

        long booksCount = bookService.countBooks();

        try {
            bookService.putBook(id, updatedBook);

            assert false;
        } catch(EntityNotFoundException e) {
            assert true;
        }

        assert bookService.countBooks() == booksCount;
        assert bookService.getBookById(id) == null;
    }

    @Test
    void testDeleteBook() {
        int id = 1;

        long booksCount = bookService.countBooks();

        bookService.deleteBookById(id);

        assert bookService.countBooks() == booksCount - 1;

        try {
            bookService.getBookById(id);

            assert false;
        } catch(EntityNotFoundException e) {
            assert true;
        }
    }

    @Test
    void testDeleteBookNotExists() {
        int id = -9999;

        try {
            bookService.deleteBookById(id);

            assert false;
        } catch(EntityNotFoundException e) {
            assert true;
        }
    }

}
