package service.impl;

import model.Paper;
import model.PaperReview;
import repository.PaperRepository;
import service.PaperService;

import javax.transaction.Transactional;
import java.util.List;

public class DefaultPaperService implements PaperService{
    private PaperRepository paperRepository;
    @Override
    public void setPaperRepository(PaperRepository paperRepository) {
        this.paperRepository = paperRepository;
    }

    @Override
    public void addPaper(Paper paper) {
        paperRepository.save(paper);
    }

    @Override
    public void updatePaper(Paper paper) {
        paperRepository.update(paper);
    }

    @Override
    public List<Paper> getAll() {
        return paperRepository.getAll();
    }

    @Transactional
    @Override
    public List<PaperReview> getReviewsForPaper(Paper paper) {
        return paperRepository.getReviewsByPaperId(paper.getId());
    }
}
