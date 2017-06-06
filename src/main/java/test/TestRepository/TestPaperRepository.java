package test.TestRepository;


import model.Paper;
import repository.impl.DefaultPaperRepository;

public class TestPaperRepository {
    public void Test()
    {
        Paper entity1 = new Paper();

        DefaultPaperRepository defaultPaperRepository = new DefaultPaperRepository();

        Integer elements = defaultPaperRepository.getAll().size();

        defaultPaperRepository.save(entity1);
        assert ((elements +1) == defaultPaperRepository.getAll().size());

//        entity1.setText("ok1");
//        defaultAbstractRepository.update(entity1);
//        assert (defaultAbstractRepository.getAll().size());

        defaultPaperRepository.delete(entity1);
        assert (elements == defaultPaperRepository.getAll().size());
    }
}
