package fr.amurotakahashi.cefimtestcda2.repositories;

import fr.amurotakahashi.cefimtestcda2.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByTitleContaining(String name);

    List<Book> findByTitleContainingIgnoreCase(String name);

    List<Book> findByTitleLike(String name);

    @Query("select b from Book b where b.pagesCount > 1000")
    List<Book> findBigBooks();

    @Query("select b from Book b where b.pagesCount > ?1")
    List<Book> findBigBooks(Integer pagesCount);

    @Query("select b from Book b where b.pagesCount > :minimumPages")
    List<Book> findBigBooksByAlias(@Param("minimumPages") Integer pagesCount);

    List<Book> findByAuthorId(int authorId);
}
