package controller;

import service.*;

public interface AuthenticationController {
    void handleLogin();
    void handleRegister();
    void setUserService(UserService userService);
    void setConferenceSessionService(ConferenceSessionService conferenceSessionService);
    void setAuthorService(AuthorService authorService);
    void setPaperService(PaperService paperService);
    void setAbstractService(AbstractService abstractService);
    void setReviewerService(ReviewerService reviewerService);
    void setSectionService(SectionService sectionService);
}
