package service;

import repository.AuthorRepository;

/**
 * Created by Sergiu on 5/22/2017.
 */
public interface AuthorService {
    void setAuthorRepository(AuthorRepository authorRepository);
    void setPaperService(PaperService paperService);
}
