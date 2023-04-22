package fr.amurotakahashi.cefimtestcda2.repositories;

import fr.amurotakahashi.cefimtestcda2.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    List<Genre> findByName(String name);
}
