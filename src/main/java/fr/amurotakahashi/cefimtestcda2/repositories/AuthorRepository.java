package fr.amurotakahashi.cefimtestcda2.repositories;

import fr.amurotakahashi.cefimtestcda2.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    List<Author> findByLastName(String lastName);
}
