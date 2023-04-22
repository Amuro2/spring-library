package fr.amurotakahashi.cefimtestcda2.services;

import fr.amurotakahashi.cefimtestcda2.entities.Genre;
import fr.amurotakahashi.cefimtestcda2.repositories.GenreRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Genre getGenreById(Integer id) {
        return genreRepository.findById(id).orElse(null);
    }

    public List<Genre> getGenresByName(String name) {
        return genreRepository.findByName(name);
    }

    public Genre postGenre(Genre genre) {
        if(genre == null) {
            throw new InvalidParameterException("Genre must not be null");
        }

        Optional<Genre> optionalGenre = genreRepository.findById(genre.getId());

        if(optionalGenre.isEmpty()) {
            return genreRepository.save(genre);
        } else {
            return optionalGenre.get();
        }
    }

    public Genre patchGenreName(Integer id, String name) {
        Optional<Genre> optionalGenre = genreRepository.findById(id);

        if(optionalGenre.isPresent()) {
            Genre genre = optionalGenre.get();

            genre.setName(name);

            return genreRepository.save(genre);
        } else {
            throw new EntityNotFoundException();
        }
    }

    public Genre putGenre(Integer id, Genre genre) {
        if(genre == null) {
            throw new InvalidParameterException("Genre must not be null");
        }

        genre.setId(id);

        Optional<Genre> optionalGenre = genreRepository.findById(id);

        if(optionalGenre.isPresent()) {
            return genreRepository.save(genre);
        } else {
            throw new EntityNotFoundException();
        }
    }

    public Genre deleteGenreById(Integer id) {
        Optional<Genre> optionalGenre = genreRepository.findById(id);

        if(optionalGenre.isPresent()) {
            genreRepository.deleteById(id);

            return optionalGenre.get();
        } else {
            throw new EntityNotFoundException();
        }
    }
}
