package test.TestRepository;


import model.Author;
import repository.impl.DefaultAuthorRepository;

public class TestAuthorRepository {

    public void Test()
    {
        Author entity1 = new Author();

        DefaultAuthorRepository defaultAuthorRepository = new DefaultAuthorRepository();

        Integer elements = defaultAuthorRepository.getAll().size();

        defaultAuthorRepository.save(entity1);
        assert ((elements +1) == defaultAuthorRepository.getAll().size());

//        entity1.setText("ok1");
//        defaultAbstractRepository.update(entity1);
//        assert (defaultAbstractRepository.getAll().size());

        defaultAuthorRepository.delete(entity1);
        assert (elements == defaultAuthorRepository.getAll().size());

    }
}
