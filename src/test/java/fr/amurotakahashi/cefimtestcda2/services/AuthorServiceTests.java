package fr.amurotakahashi.cefimtestcda2.services;

import fr.amurotakahashi.cefimtestcda2.entities.Author;
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
public class AuthorServiceTests {
    @Autowired
    private AuthorService authorService;

    @Test
    void testGetAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();

        System.out.println(authors);

    }

    @Test
    void testGetAuthorById() {
        int id = 1;
        Author author = authorService.getAuthorById(id);

        System.out.println(author);

        assert author != null;

    }

    @Test
    void testGetAuthorByIdNotExists() {
        int id = -9999;

        try {
            authorService.getAuthorById(id);

            assert false;
        } catch(EntityNotFoundException e) {
            assert true;
        }
    }

    @Test
    void testPostAuthor() {
        long authorsCount = authorService.countAuthors();

        Author author = new Author();

        authorService.postAuthor(author);

        assert authorService.countAuthors() == authorsCount + 1;

    }

    @Test
    void testPostAuthorAlreadyExists() {
        Author author = authorService.getAuthorById(1);

        try {
            authorService.postAuthor(author);

            assert false;
        } catch(EntityExistsException e) {
            assert true;
        }
    }

    @Test
    void testPostAuthorBadRequest() {
        try {
            authorService.postAuthor(null);

            assert false;
        } catch(InvalidParameterException e) {
            assert true;
        }
    }

    @Test
    void testPutAuthor() {
        int id = 1;
        Author author = new Author();

        authorService.putAuthor(id, author);


    }

    @Test
    void testPutAuthorNotExists() {
        int id = -9999;
        Author author = new Author();

        try {
            authorService.putAuthor(id, author);

            assert false;
        } catch(EntityNotFoundException e) {
            assert true;
        }
    }

    @Test
    void testPutAuthorBadRequest() {
        int id = 1;

        try {
            authorService.putAuthor(id, null);

            assert false;
        } catch(InvalidParameterException e) {
            assert true;
        }
    }

    @Test
    void testDeleteAuthor() {
        int id = 1;

        long authorsCount = authorService.countAuthors();

        authorService.deleteAuthorById(id);

        assert authorService.countAuthors() == authorsCount - 1;

        try {
            authorService.getAuthorById(id);

            assert false;
        } catch(EntityNotFoundException e) {
            assert true;
        }
    }

    @Test
    void testDeleteAuthorNotExists() {
        int id = -9999;

        try {
            authorService.deleteAuthorById(id);

            assert false;
        } catch(EntityNotFoundException e) {
            assert true;
        }
    }

}
