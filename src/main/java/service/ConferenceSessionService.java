package service;

import model.ConferenceSession;
import repository.ConferenceSessionRepository;

public interface ConferenceSessionService {
    ConferenceSession findById(Integer id);
    void setConferenceSessionRepository(ConferenceSessionRepository conferenceSessionRepository);
}
