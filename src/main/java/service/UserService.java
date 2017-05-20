package service;

import model.User;
import repository.UserRepository;

import java.util.List;

/**
 * Created by raulp on 5/20/2017.
 */
public interface UserService {
    void save(User user);
    void update(User user);
    void deleteById(Integer id);
    User findById(Integer id);
    User findByEmail(String email);
    List<User> getAll();
    void setUserRepository(UserRepository userRepository);
}
