package service.impl;

import model.Reviewer;
import model.User;
import org.hibernate.Hibernate;
import repository.ReviewerRepository;
import service.ReviewerService;

import javax.transaction.Transactional;

public class DefaultReviewerService implements ReviewerService {
    private ReviewerRepository reviewerRepository;

    @Transactional
    @Override
    public Reviewer getReviewerByUser(User user) {
        Reviewer reviewer = reviewerRepository.getReviewerByUserId(user.getId());
        return reviewer;
    }

    @Override
    public void setReviewerRepository(ReviewerRepository repository) {
        this.reviewerRepository = repository;
    }

    @Transactional
    @Override
    public void save(Reviewer reviewer) {
        reviewerRepository.save(reviewer);
    }

    @Transactional
    @Override
    public void update(Reviewer reviewer) {
        reviewerRepository.update(reviewer);
    }

}
