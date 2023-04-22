package fr.amurotakahashi.cefimtestcda2.controllers;

import fr.amurotakahashi.cefimtestcda2.ControllerTests;
import fr.amurotakahashi.cefimtestcda2.entities.Book;
import fr.amurotakahashi.cefimtestcda2.services.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class BookControllerTests extends ControllerTests {
    @Autowired
    private BookService bookService;

    @Test
    void testGetAllBooks() throws Exception {
        String contentAsString = get("/api/book/all");
        List<Book> books = Arrays.asList(objectMapper.readValue(contentAsString, Book[].class));

        assert books.size() > 0;
    }

    @Test
    void testGetBookById() throws Exception {
        String contentAsString = get("/api/book/1");
        Book book = objectMapper.readValue(contentAsString, Book.class);

        assert book.getId().equals(1);
    }

    @Test
    void testGetBookByIdNotExists() throws Exception {
        request(HttpMethod.GET, "/api/book/-9999", HttpStatus.NOT_FOUND);
    }

    @Test
    void testPostBook() throws Exception {
        long booksCount = bookService.countBooks();

        Book book = Book.nouveauLivreQuiNExistaitPas;

        String contentAsString = post("/api/book", book);
        System.out.println(contentAsString);
        assert bookService.countBooks() == booksCount + 1;
    }

    @Test
    void testPostBookAlreadyExists() throws Exception {
        long booksCount = bookService.countBooks();

        Book book = Book.madameBovary;
        book.setId(1);

        request(HttpMethod.POST, "/api/book", book, HttpStatus.CONFLICT);
        assert bookService.countBooks() == booksCount;
    }

    @Test
    void testPostBookBadRequest() throws Exception {
        request(HttpMethod.POST, "/api/book", new StringBuilder(), HttpStatus.BAD_REQUEST);
        request(HttpMethod.POST, "/api/book", null, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testPatchBook() throws Exception {
        Integer pagesCount = 130000;
        String contentAsString = patch("/api/book/1/pages", pagesCount);
        Book book = objectMapper.readValue(contentAsString, Book.class);

        System.out.println(contentAsString);

        assert book.getPagesCount().equals(pagesCount);
    }

    @Test
    void testPatchBookNotExists() throws Exception {
        request(HttpMethod.PATCH, "/api/book/-9999/pages", 130000, HttpStatus.NOT_FOUND);
    }

    @Test
    void testPatchBookBadRequest() throws Exception {
        request(HttpMethod.PATCH, "/api/book/1/pages", new StringBuilder(), HttpStatus.BAD_REQUEST);
        request(HttpMethod.PATCH, "/api/book/1/pages", null, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testPutBook() throws Exception {
        int id = 1;
        Book book1 = bookService.getBookById(id);
        Book book = new Book("Non mais en fait ce titre-là est mieux", "Description qui tue.", 42, "ABIME", null);

        String contentAsString = put("/api/book/" + id, book);
        System.out.println(contentAsString);
    }

    @Test
    void testPutBookNotExists() throws Exception {
        Book book = new Book("Non mais en fait ce titre-là est mieux", "Description qui tue.", 42, "ABIME", null);

        request(HttpMethod.PUT, "/api/book/-9999", book, HttpStatus.NOT_FOUND);
    }

    @Test
    void testPutBookBadRequest() throws Exception {
        request(HttpMethod.PUT, "/api/book/1", new StringBuilder("uhfezihfzefhzeiogh"), HttpStatus.BAD_REQUEST);
        request(HttpMethod.PUT, "/api/book/1", null, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testDeleteBookExists() throws Exception {
        long countBefore = bookService.countBooks();

        int id = 1;
        String contentAsString = delete("/api/book/" + id);
        Book book = objectMapper.readValue(contentAsString, Book.class);

        System.out.println(contentAsString);

        assert bookService.countBooks() == countBefore - 1;
        assert bookService.getBookById(id) == null;

        assert book != null;
        assert book.getId().equals(id);
    }

    @Test
    void testDeleteBookNotExists() throws Exception {
        request(HttpMethod.DELETE, "/api/book/-9999", HttpStatus.NOT_FOUND);
    }

    @Test
    void testGetBookByTitle() throws Exception {

    }

}
