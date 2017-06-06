package service.impl;

import model.Author;
import model.Paper;
import model.User;
import repository.AuthorRepository;
import service.AuthorService;
import service.PaperService;

public class DefaultAuthorService implements AuthorService {
    private AuthorRepository authorRepository;
    private PaperService paperService;

    @Override
    public void setAuthorRepository(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void setPaperService(PaperService paperService) {
        this.paperService = paperService;
    }

    @Override
    public Author getAuthorByUserId(User uid) {
        return authorRepository.getAuthorByUserId(uid);
    }

    @Override
    public void addAuthor(Author author) {
        authorRepository.save(author);
    }

    @Override
    public void updateAuthor(Author author) {
        authorRepository.update(author);
    }


}
