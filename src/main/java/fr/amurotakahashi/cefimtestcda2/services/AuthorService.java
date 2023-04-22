package fr.amurotakahashi.cefimtestcda2.services;

import fr.amurotakahashi.cefimtestcda2.entities.Author;
import fr.amurotakahashi.cefimtestcda2.entities.plus.AuthorWithBooks;
import fr.amurotakahashi.cefimtestcda2.repositories.AuthorRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookService bookService;

    public long countAuthors() {
        return authorRepository.count();
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(Integer id) {
        return authorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Author getAuthor(Author author) {
        if(author == null) {
            throw new InvalidParameterException("Author must not be null");
        }

        return getAuthorById(author.getId());
    }

    public List<Author> getAuthorsByLastName(String lastName) {
        return authorRepository.findByLastName(lastName);
    }

    public Author postAuthor(Author author) {
        if(author == null) {
            throw new InvalidParameterException("Author must not be null");
        }

        if(author.getId() == null) {
            return authorRepository.save(author);
        } else {
            Optional<Author> optionalAuthor = authorRepository.findById(author.getId());

            if(optionalAuthor.isEmpty()) {
                return authorRepository.save(author);
            } else {
                throw new EntityExistsException();
            }
        }

    }

    public Author patchAuthorLastName(Integer id, String lastName) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);

        if(optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();

            author.setLastName(lastName);

            return authorRepository.save(author);
        } else {
            throw new EntityNotFoundException();
        }
    }

    public Author putAuthor(Integer id, Author author) {
        if(author == null) {
            throw new InvalidParameterException("Author must not be null");
        }

        author.setId(id);

        Optional<Author> optionalAuthor = authorRepository.findById(id);

        if(optionalAuthor.isPresent()) {
            return authorRepository.save(author);
        } else {
            throw new EntityNotFoundException();
        }
    }

    public Author deleteAuthorById(Integer id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);

        if(optionalAuthor.isPresent()) {
            authorRepository.deleteById(id);

            return optionalAuthor.get();
        } else {
            throw new EntityNotFoundException();
        }
    }

    public AuthorWithBooks getAuthorWithBooks() {
        return null;
    }
}
