package repository;

import model.Paper;
import java.util.List;

public interface PaperRepository {
    void save(Paper paper);
    void update(Paper paper);
    Paper getPaperById(Integer id);
    List<Paper> getAll();
}
