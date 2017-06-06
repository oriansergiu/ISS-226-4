package test.TestRepository;


import model.ConferenceSession;
import repository.impl.DefaultConferenceSessionRepository;

public class TestConferenceSessionRepository {
    public void Test()
    {
        ConferenceSession entity1 = new ConferenceSession();

        DefaultConferenceSessionRepository defaultConferenceSessionRepository = new DefaultConferenceSessionRepository();

        Integer elements = defaultConferenceSessionRepository.getAll().size();

        defaultConferenceSessionRepository.save(entity1);
        assert ((elements +1) == defaultConferenceSessionRepository.getAll().size());

//        entity1.setText("ok1");
//        defaultAbstractRepository.update(entity1);
//        assert (defaultAbstractRepository.getAll().size());

        defaultConferenceSessionRepository.delete(entity1);
        assert (elements == defaultConferenceSessionRepository.getAll().size());

    }
}
