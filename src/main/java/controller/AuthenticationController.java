package controller;

import service.AuthorService;
import service.ConferenceSessionService;
import service.PaperService;
import service.UserService;

public interface AuthenticationController {
    void handleLogin();
    void handleRegister();
    void setUserService(UserService userService);
    void setConferenceSessionService(ConferenceSessionService conferenceSessionService);
    void setAuthorService(AuthorService authorService);
    void setPaperService(PaperService paperService);
}