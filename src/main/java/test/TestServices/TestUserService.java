package test.TestServices;

import model.User;
import repository.UserRepository;
import repository.impl.DefaultUserRepository;
import service.impl.DefaultUserService;
import validator.exceptions.UserException;

public class TestUserService {
    public void tests()
    {

        DefaultUserRepository userRepository = new DefaultUserRepository();
        DefaultUserService defaultUserService = new DefaultUserService();

        defaultUserService.setUserRepository(userRepository);

        User user1 = new User();
        user1.setEmail("email1");

        try {
            defaultUserService.save(user1);
            assert(true);
        } catch (UserException e) {
            assert (false);
        }

        try {
            defaultUserService.save(user1);
            assert(false);
        } catch (UserException e) {
            assert (true);
        }

        try {
            defaultUserService.loginUser(null,"x");
            assert(false);
        } catch (UserException e) {
            assert (true);
        }

        defaultUserService.delete(user1);



    }
}
