package test.TestRepository;


import model.User;
import repository.impl.DefaultUserRepository;

public class TestUserRepository {
    public void Test()
    {
        User entity1 = new User();

        DefaultUserRepository defaultUserRepository = new DefaultUserRepository();

        Integer elements = defaultUserRepository.getAll().size();

        defaultUserRepository.save(entity1);
        assert ((elements +1) == defaultUserRepository.getAll().size());

//        entity1.setText("ok1");
//        defaultAbstractRepository.update(entity1);
//        assert (defaultAbstractRepository.getAll().size());

        defaultUserRepository.delete(entity1);
        assert (elements == defaultUserRepository.getAll().size());
    }
}
