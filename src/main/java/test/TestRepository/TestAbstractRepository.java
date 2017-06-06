package test.TestRepository;

import model.Abstract;
import model.Paper;
import repository.impl.DefaultAbstractRepository;
import service.impl.DefaultAbstractService;


public class TestAbstractRepository {

    public void Test()
    {

        Abstract entity1 = new Abstract();

        DefaultAbstractRepository defaultAbstractRepository = new DefaultAbstractRepository();

        Integer elements = defaultAbstractRepository.getAll().size();

        defaultAbstractRepository.save(entity1);
        assert ((elements +1) == defaultAbstractRepository.getAll().size());

//        entity1.setText("ok1");
//        defaultAbstractRepository.update(entity1);
//        assert (defaultAbstractRepository.getAll().size());

        defaultAbstractRepository.delete(entity1);
        assert (elements == defaultAbstractRepository.getAll().size());


    }
}
