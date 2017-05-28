package repository;

import model.Abstract;
import java.util.List;

public interface AbstractRepository {
    void save(Abstract _abstract);
    void update(Abstract _abstract);
    Abstract getAbstractById(Integer id);
    List<Abstract> getAll();
}
