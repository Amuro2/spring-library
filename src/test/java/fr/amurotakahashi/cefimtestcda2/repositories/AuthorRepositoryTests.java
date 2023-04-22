package fr.amurotakahashi.cefimtestcda2.repositories;

import fr.amurotakahashi.cefimtestcda2.entities.Author;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Transactional
public class AuthorRepositoryTests {
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void testFindAll() {
        List<Author> authors = authorRepository.findAll();

//        System.out.println(authors);
        System.out.println(authors.get(0).getBirthDate());
    }

}
