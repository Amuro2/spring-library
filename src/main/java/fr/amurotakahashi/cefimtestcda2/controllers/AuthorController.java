package fr.amurotakahashi.cefimtestcda2.controllers;

import fr.amurotakahashi.cefimtestcda2.entities.Author;
import fr.amurotakahashi.cefimtestcda2.services.AuthorService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/author")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping("/all")
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(authorService.getAuthorById(id));
        } catch(EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/search")
    public List<Author> getAuthorsByLastName(@RequestParam String lastName) {
        return authorService.getAuthorsByLastName(lastName);
    }

    @PostMapping
    public ResponseEntity<Author> postAuthor(@RequestBody @Valid Author author) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(authorService.postAuthor(author));
        } catch(EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(authorService.getAuthor(author));
        }
    }

    @PatchMapping("/{id}/lastName")
    public ResponseEntity<Author> patchAuthorLastName(@PathVariable Integer id, @RequestBody String lastName) {
        try {
            return ResponseEntity.ok(authorService.patchAuthorLastName(id, lastName));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> putAuthor(@PathVariable Integer id, @RequestBody @Valid Author author) {
        try {
            return ResponseEntity.ok(authorService.putAuthor(id, author));
        } catch(EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Author> deleteAuthorById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(authorService.deleteAuthorById(id));
        } catch(EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
