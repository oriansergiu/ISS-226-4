package service.impl;

import model.Section;
import repository.SectionRepository;
import service.SectionService;

import java.util.List;

/**
 * Created by Sergiu on 5/22/2017.
 */
public class DefaultSectionService implements SectionService {
    private SectionRepository sectionRepository;


    @Override
    public void addSection(Section section) {
        sectionRepository.save(section);
    }

    @Override
    public void setSectionRepository(SectionRepository repository) {
        this.sectionRepository = repository;
    }

    @Override
    public List<Section> getAll(){return sectionRepository.getAll();}
}
