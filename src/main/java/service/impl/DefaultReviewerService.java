package service.impl;

import enums.AcceptanceType;
import model.Paper;
import model.PaperReview;
import model.Reviewer;
import model.User;
import repository.ReviewerRepository;
import service.ReviewerService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public PaperReview reviewPaper(Reviewer reviewer, Paper paper, String comment, AcceptanceType qualifier) {
        PaperReview paperReview = new PaperReview();
        paperReview.setPaper(paper);
        paperReview.setReviewer(reviewer);
        paperReview.setQualifier(qualifier.ordinal());
        paperReview.setComment(comment);

        reviewerRepository.reviewPaper(paperReview);

        return paperReview;
    }
}
