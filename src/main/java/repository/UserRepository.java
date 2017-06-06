package repository;

import model.User;

import java.util.List;

public interface UserRepository {
    void save(User user);
    void update(User user);
    User getUserById(Integer id);
    User getUserByEmail(String email);
    List<User> getAll();
    void delete(User user);
}
