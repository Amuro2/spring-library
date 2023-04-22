package fr.amurotakahashi.cefimtestcda2.controllers;

import fr.amurotakahashi.cefimtestcda2.ControllerTests;
import fr.amurotakahashi.cefimtestcda2.entities.Author;
import fr.amurotakahashi.cefimtestcda2.services.AuthorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class AuthorControllerTests extends ControllerTests {
    @Autowired
    private AuthorService authorService;

    @Test
    void testGetAllAuthors() throws Exception {
        String contentAsString = get("/api/author/all");
        List<Author> authors = Arrays.asList(objectMapper.readValue(contentAsString, Author[].class));

        System.out.println(authors);

        assert authors.size() > 0;
    }

    @Test
    void testGetAuthorById() throws Exception {
        int id = 1;
        String contentAsString = get("/api/author/" + id);
        Author author = objectMapper.readValue(contentAsString, Author.class);

        assert author.getId().equals(id);
    }

    @Test
    void testGetAuthorByIdNotExists() throws Exception {
        request(HttpMethod.GET, "/api/author/-9999", HttpStatus.NOT_FOUND);
    }

    @Test
    void testGetAuthorByName() throws Exception {
        String contentAsString = get("/api/author/search?name=");
        List<Author> authors = Arrays.asList(objectMapper.readValue(contentAsString, Author[].class));

        System.out.println(authors);
    }

    @Test
    void testPostAuthor() throws Exception {
        long authorsCount = authorService.countAuthors();

        Author author = new Author();

        String contentAsString = post("/api/author", author);

        System.out.println(contentAsString);

        assert authorService.countAuthors() == authorsCount + 1;
    }

    @Test
    void testPostAuthorAlreadyExists() throws Exception {
        long authorsCount = authorService.countAuthors();

        Author author = new Author();
        author.setId(1);

        request(HttpMethod.POST, "/api/author", author, HttpStatus.CONFLICT);
        assert authorService.countAuthors() == authorsCount;
    }

    @Test
    void testPostAuthorBadRequest() throws Exception {
        request(HttpMethod.POST, "/api/author", new StringBuilder(), HttpStatus.BAD_REQUEST);
        request(HttpMethod.POST, "/api/author", null, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testPatchAuthor() throws Exception {
        String lastName = "Lorem ipsum";
        String contentAsString = patch("/api/author/1/lastName", lastName);
        Author author = objectMapper.readValue(contentAsString, Author.class);

        System.out.println(contentAsString);

        assert author.getLastName().equals(lastName);
    }

    @Test
    void testPatchAuthorNotExists() throws Exception {
        request(HttpMethod.PATCH, "/api/author/-9999/name", "Lorem ipsum", HttpStatus.NOT_FOUND);
    }

    @Test
    void testPatchAuthorBadRequest() throws Exception {
        request(HttpMethod.PATCH, "/api/author/1/name", null, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testPutAuthor() throws Exception {
        int id = 1;
        Author author1 = authorService.getAuthorById(id);
        Author author = new Author();

        String contentAsString = put("/api/author/" + id, author);
        System.out.println(contentAsString);
    }

    @Test
    void testPutAuthorNotExists() throws Exception {
        Author author = new Author();

        request(HttpMethod.PUT, "/api/author/-9999", author, HttpStatus.NOT_FOUND);
    }

    @Test
    void testPutAuthorBadRequest() throws Exception {
        request(HttpMethod.PUT, "/api/author/1", new StringBuilder("uhfezihfzefhzeiogh"), HttpStatus.BAD_REQUEST);
        request(HttpMethod.PUT, "/api/author/1", null, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testDeleteAuthor() throws Exception {
        long authorsCount = authorService.countAuthors();

        int id = 1;
        String contentAsString = delete("/api/author/" + id);
        Author author = objectMapper.readValue(contentAsString, Author.class);

        System.out.println(contentAsString);

        assert authorService.countAuthors() == authorsCount - 1;
        assert authorService.getAuthorById(id) == null;

        assert author != null;
        assert author.getId().equals(id);
    }

    @Test
    void testDeleteAuthorNotExists() throws Exception {
        request(HttpMethod.DELETE, "/api/author/-9999", HttpStatus.NOT_FOUND);
    }

}
