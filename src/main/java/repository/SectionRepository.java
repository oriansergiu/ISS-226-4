package repository;

import model.Section;

import java.util.List;

public interface SectionRepository {
    void save(Section conferenceSession);
    void update(Section conferenceSession);
    Section findById(Integer id);
    List<Section> getAll();

    void delete(Section section);
}
