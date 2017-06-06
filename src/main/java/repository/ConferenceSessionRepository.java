package repository;

import model.ConferenceSession;

import java.util.List;

public interface ConferenceSessionRepository {
    void save(ConferenceSession conferenceSession);
    void update(ConferenceSession conferenceSession);
    ConferenceSession findById(Integer id);
    List<ConferenceSession> getAll();

    void delete(ConferenceSession conferenceSession);
}
