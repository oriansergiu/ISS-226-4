package test.TestRepository;

import model.Section;
import repository.impl.DefaultSectionRepository;

public class TestSectionRepository {
    public void Test()
    {
        Section entity1 = new Section();

        DefaultSectionRepository defaultSectionRepository = new DefaultSectionRepository();

        Integer elements = defaultSectionRepository.getAll().size();

        defaultSectionRepository.save(entity1);
        assert ((elements +1) == defaultSectionRepository.getAll().size());

//        entity1.setText("ok1");
//        defaultAbstractRepository.update(entity1);
//        assert (defaultAbstractRepository.getAll().size());

        defaultSectionRepository.delete(entity1);
        assert (elements == defaultSectionRepository.getAll().size());
    }
}
