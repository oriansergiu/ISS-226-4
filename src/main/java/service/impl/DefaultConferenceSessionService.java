package service.impl;

import model.ConferenceSession;
import repository.ConferenceSessionRepository;
import service.ConferenceSessionService;

import javax.transaction.Transactional;

public class DefaultConferenceSessionService implements ConferenceSessionService {
    private ConferenceSessionRepository conferenceSessionRepository;

    @Transactional
    public ConferenceSession findById(Integer id) {
        return conferenceSessionRepository.findById(id);
    }

    @Transactional
    public void setConferenceSessionRepository(ConferenceSessionRepository conferenceSessionRepository) {
        this.conferenceSessionRepository = conferenceSessionRepository;
    }
}
