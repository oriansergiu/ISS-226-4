package service.impl;

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
}
