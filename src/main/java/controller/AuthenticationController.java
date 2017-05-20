package controller;

import service.ConferenceSessionService;
import service.UserService;

public interface AuthenticationController {
    void handleLogin();
    void handleRegister();
    void setUserService(UserService userService);
    void setConferenceSessionService(ConferenceSessionService conferenceSessionService);
}
