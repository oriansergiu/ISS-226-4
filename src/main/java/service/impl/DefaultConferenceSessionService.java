package service.impl;

import model.ConferenceSession;
import repository.ConferenceSessionRepository;
import service.ConferenceSessionService;

import javax.transaction.Transactional;
import java.util.List;

public class DefaultConferenceSessionService implements ConferenceSessionService {
    private ConferenceSessionRepository conferenceSessionRepository;

    @Transactional
    public ConferenceSession findById(Integer id) {
        return conferenceSessionRepository.findById(id);
    }

    @Override
    public List<ConferenceSession> getAll(){return conferenceSessionRepository.getAll();}

    @Transactional
    public void setConferenceSessionRepository(ConferenceSessionRepository conferenceSessionRepository) {
        this.conferenceSessionRepository = conferenceSessionRepository;
    }

    @Override
    public void updateConferenceSession(ConferenceSession conferenceSession) {
        conferenceSessionRepository.update(conferenceSession);
    }

}
