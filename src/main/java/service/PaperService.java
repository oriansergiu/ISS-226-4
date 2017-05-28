package service;

import model.Paper;
import repository.PaperRepository;

public interface PaperService {
    void setPaperRepository(PaperRepository paperRepository);
    void addPaper(Paper paper);
    void updatePaper(Paper paper);
}
