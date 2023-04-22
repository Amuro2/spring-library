package fr.amurotakahashi.cefimtestcda2.entities;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthorTests {

    @Test
    void testNewAuthor() {
        Author author = new Author("DUPONDT", "Martin", null);

        System.out.println(author);
    }

}
