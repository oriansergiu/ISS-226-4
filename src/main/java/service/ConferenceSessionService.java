package service;

import model.ConferenceSession;
import repository.ConferenceSessionRepository;

import java.util.List;

public interface ConferenceSessionService {
    ConferenceSession findById(Integer id);
    List<ConferenceSession> getAll();
    void setConferenceSessionRepository(ConferenceSessionRepository conferenceSessionRepository);
    void updateConferenceSession(ConferenceSession conferenceSession);
}
