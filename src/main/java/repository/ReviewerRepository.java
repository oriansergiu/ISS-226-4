package repository;

import model.Reviewer;

public interface ReviewerRepository {
    void save(Reviewer reviewer);
    void update(Reviewer reviewer);
    Reviewer getReviewerByUserId(Integer id);
}
