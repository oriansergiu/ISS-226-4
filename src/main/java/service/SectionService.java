package service;

import model.Section;
import repository.SectionRepository;

import java.util.List;

/**
 * Created by Sergiu on 5/22/2017.
 */
public interface SectionService {

    void setSectionRepository(SectionRepository repository);
    void addSection(Section section);
    List<Section> getAll();
}
