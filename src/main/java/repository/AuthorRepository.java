package repository;

import model.Author;

import java.util.List;

public interface AuthorRepository {
    void save(Author author);
    void update(Author author);
    Author getAuthorById(Integer id);
    List<Author> getAll();
}
