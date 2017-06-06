package test.TestRepository;


import model.Reviewer;
import repository.impl.DefaultReviewerRepository;

public class TestReviewerRepository {
    public void Test()
    {
        Reviewer entity1 = new Reviewer();

        DefaultReviewerRepository defaultReviewerRepository = new DefaultReviewerRepository();

        Integer elements = defaultReviewerRepository.getAll().size();

        defaultReviewerRepository.save(entity1);
        assert ((elements +1) == defaultReviewerRepository.getAll().size());

//        entity1.setText("ok1");
//        defaultAbstractRepository.update(entity1);
//        assert (defaultAbstractRepository.getAll().size());

        defaultReviewerRepository.delete(entity1);
        assert (elements == defaultReviewerRepository.getAll().size());
    }
}
