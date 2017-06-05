package repository;

import model.PaperReview;
import model.Reviewer;

public interface ReviewerRepository {
    void save(Reviewer reviewer);
    void update(Reviewer reviewer);
    Reviewer getReviewerByUserId(Integer id);
    void reviewPaper(PaperReview paperReview);
}
