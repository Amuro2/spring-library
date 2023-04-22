package fr.amurotakahashi.cefimtestcda2.services;

import fr.amurotakahashi.cefimtestcda2.repositories.BookRepository;
import fr.amurotakahashi.cefimtestcda2.entities.Book;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public String helloBook() {
        return "Hello Book !!!";
    }

    public long countBooks() {
        return bookRepository.count();
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(int id) {
        return bookRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Book getBook(Book book) {
        if(book == null) {
            throw new InvalidParameterException("Book must not be null");
        }

        return getBookById(book.getId());
    }

    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findByTitleContaining(title);
    }

    public List<Book> getBooksByTitleCaseInsensitive(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Book> getBooksByAuthorId(Integer authorId) {
        return bookRepository.findByAuthorId(authorId);
    }

    public Book postBook(Book book) {
        if(book == null) {
            throw new InvalidParameterException("Book must not be null");
        }

        if(book.getId() == null) {
            return bookRepository.save(book);
        } else {
            Optional<Book> optionalBook = bookRepository.findById(book.getId());

            if(optionalBook.isEmpty()) {
                return bookRepository.save(book);
            } else {
                throw new EntityExistsException();
            }
        }
    }

    public Book patchBookPages(int id, Integer pagesCount) {
        Optional<Book> optionalBook = bookRepository.findById(id);

        if(optionalBook.isPresent()) {
            Book book = optionalBook.get();

            book.setPagesCount(pagesCount);

            return bookRepository.save(book);
        } else {
            throw new EntityNotFoundException();
        }
    }

    public Book putBook(int id, Book book) {
        if(book == null) {
            throw new InvalidParameterException("Book must not be null");
        }

        book.setId(id);

        Optional<Book> optionalBook = bookRepository.findById(id);

        if(optionalBook.isPresent()) {
            return bookRepository.save(book);
        } else {
            throw new EntityNotFoundException();
        }
    }

    public Book putBook(Book book) {
        if(book == null) {
            throw new InvalidParameterException("Book must not be null");
        }

        return putBook(book.getId(), book);
    }

    public Book deleteBookById(int id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        bookRepository.deleteById(id);

        return optionalBook.orElseThrow(EntityNotFoundException::new);
    }
}
