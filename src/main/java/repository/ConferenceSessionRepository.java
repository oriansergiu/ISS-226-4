package repository;

import model.ConferenceSession;

public interface ConferenceSessionRepository {
    ConferenceSession findById(Integer id);
}
