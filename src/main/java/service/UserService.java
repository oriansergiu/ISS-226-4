package service;

import model.User;
import repository.UserRepository;
import validator.exceptions.UserException;

import java.util.List;

public interface UserService {
    void save(User user) throws UserException;
    void update(User user);
    void deleteById(Integer id);
    User findById(Integer id);
    User findByEmail(String email);
    List<User> getAll();
    void setUserRepository(UserRepository userRepository);
    User loginUser(String email, String password) throws UserException;
    void delete(User user);
}
