package service;

import model.Abstract;
import repository.AbstractRepository;

public interface AbstractService {
    void setAbstractRepository(AbstractRepository abstractRepository);
    void addAbstract(Abstract _abstract);
    void updateAbstract(Abstract _abstract);
    void delete(Abstract _abstract);
}
