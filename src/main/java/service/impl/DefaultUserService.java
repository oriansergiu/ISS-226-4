package service.impl;

import model.User;
import repository.UserRepository;
import service.UserService;

import javax.transaction.Transactional;
import java.util.List;

public class DefaultUserService implements UserService {
    private UserRepository userRepository;

    @Transactional
    public void save(User user) {
        User persistedUser = userRepository.getUserByEmail(user.getEmail());
        if (persistedUser == null) {
            userRepository.save(user);
        }
    }

    @Transactional
    public void update(User user) {

    }

    @Transactional
    public void deleteById(Integer id) {

    }

    @Transactional
    public User findById(Integer id) {
        return null;
    }

    @Transactional
    public User findByEmail(String email) {
        return null;
    }

    @Transactional
    public List<User> getAll() {
        return null;
    }

    @Transactional
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
