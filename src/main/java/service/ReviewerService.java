package service;

import enums.AcceptanceType;
import model.Paper;
import model.Reviewer;
import model.User;
import repository.ReviewerRepository;

public interface ReviewerService {
    Reviewer getReviewerByUser(User user);
    void setReviewerRepository(ReviewerRepository repository);
    void save(Reviewer reviewer);
    void update(Reviewer reviewer);
    void reviewPaper(Reviewer reviewer, Paper paper, String comment, AcceptanceType qualifier);
}
