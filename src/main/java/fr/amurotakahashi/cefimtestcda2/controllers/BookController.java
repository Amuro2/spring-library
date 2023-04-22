package fr.amurotakahashi.cefimtestcda2.controllers;

import fr.amurotakahashi.cefimtestcda2.entities.Book;
import fr.amurotakahashi.cefimtestcda2.services.BookService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/all")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(bookService.getBookById(id));
        } catch(EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/search")
    public List<Book> getBooksByTitle(@RequestParam String title) {
        return bookService.getBooksByTitle(title);
    }

    @PostMapping
    public ResponseEntity<Book> postBook(@RequestBody @Valid Book book) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(bookService.postBook(book));
        } catch(EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(bookService.getBook(book));
        }
    }

    @PatchMapping("/{id}/pages")
    public ResponseEntity<Book> patchBookPages(@PathVariable int id, @RequestBody @Positive Integer pages) {
        try {
            return ResponseEntity.ok(bookService.patchBookPages(id, pages));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> putBook(@PathVariable int id, @RequestBody @Valid Book book) {
        try {
            return ResponseEntity.ok(bookService.putBook(id, book));
        } catch(EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBookById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(bookService.deleteBookById(id));
        } catch(EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
