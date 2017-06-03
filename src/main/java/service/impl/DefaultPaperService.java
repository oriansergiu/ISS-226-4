package service.impl;

import model.Paper;
import repository.PaperRepository;
import service.PaperService;

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
}
