package service;

import model.Reviewer;
import model.User;
import repository.ReviewerRepository;

public interface ReviewerService {
    Reviewer getReviewerByUser(User user);
    void setReviewerRepository(ReviewerRepository repository);
    void save(Reviewer reviewer);
    void update(Reviewer reviewer);
}
