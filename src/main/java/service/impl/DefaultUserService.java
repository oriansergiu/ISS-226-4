package service.impl;

import model.User;
import repository.UserRepository;
import service.UserService;
import util.UserUtil;
import validator.exceptions.UserException;

import javax.transaction.Transactional;
import java.util.List;

public class DefaultUserService implements UserService {
    private UserRepository userRepository;

    @Transactional
    public void save(User user) throws UserException {
        User persistedUser = userRepository.getUserByEmail(user.getEmail());
        if (persistedUser == null) {
            userRepository.save(user);
        }
        else {
            throw new UserException("There is already a user with this email. Please choose another one and try again.");
        }
    }

    @Transactional
    public void update(User user) {
        userRepository.update(user);
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

    @Transactional
    public User loginUser(String email, String password) throws UserException{
        String hashedPassword = UserUtil.hashPassword(password);

        User user = userRepository.getUserByEmail(email);

        if (user == null) {
            throw new UserException("Wrong credentials.");
        }

        if (!user.getPassword().equals(hashedPassword)) {
            throw new UserException("Wrong credentials.");
        }

        return user;
    }
    @Transactional
    public void delete(User user) {
        userRepository.delete(user);
    }
}
