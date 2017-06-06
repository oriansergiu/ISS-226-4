package repository;

import model.PaperReview;
import model.Reviewer;

import java.util.List;

public interface ReviewerRepository {
    void save(Reviewer reviewer);
    void update(Reviewer reviewer);
    Reviewer getReviewerByUserId(Integer id);
    void reviewPaper(PaperReview paperReview);
    List<Reviewer> getAll();
    void delete(Reviewer reviewer);
}
