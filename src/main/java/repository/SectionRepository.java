package repository;

import model.Section;

import java.util.List;

/**
 * Created by Sergiu on 5/22/2017.
 */
public interface SectionRepository {
    void save(Section conferenceSession);
    void update(Section conferenceSession);
    Section findById(Integer id);
    List<Section> getAll();
}
