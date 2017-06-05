package service;

import model.Paper;
import model.PaperReview;
import repository.PaperRepository;

import java.util.List;

public interface PaperService {
    void setPaperRepository(PaperRepository paperRepository);
    void addPaper(Paper paper);
    void updatePaper(Paper paper);
    List<Paper> getAll();
    List<PaperReview> getReviewsForPaper(Paper paper);
}
