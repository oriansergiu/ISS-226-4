package repository;

import model.Author;
import model.User;

import java.util.List;

public interface AuthorRepository {
    void save(Author author);
    void update(Author author);
    void delete(Author author);
    Author getAuthorById(Integer id);
    Author getAuthorByUserId(User id);
    List<Author> getAll();
}
