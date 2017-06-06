package service;

import model.Author;
import model.User;
import repository.AuthorRepository;

import java.util.List;


public interface AuthorService {
    void setAuthorRepository(AuthorRepository authorRepository);
    void setPaperService(PaperService paperService);
    Author getAuthorByUserId(User uid);
    void addAuthor(Author author);
    void updateAuthor(Author author);
    List<Author> getAll();
}
