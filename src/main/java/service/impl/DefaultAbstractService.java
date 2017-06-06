package service.impl;

import model.Abstract;
import repository.AbstractRepository;
import service.AbstractService;



public class DefaultAbstractService implements AbstractService{
    private AbstractRepository abstractRepository;

    @Override
    public void setAbstractRepository(AbstractRepository abstractRepository) {
        this.abstractRepository = abstractRepository;
    }

    @Override
    public void addAbstract(Abstract _abstract) {
        abstractRepository.save(_abstract);
    }

    @Override
    public void updateAbstract(Abstract _abstract) {
        abstractRepository.update(_abstract);
    }

    @Override
    public void delete(Abstract _abstract) {
        abstractRepository.delete(_abstract);
    }
}
