package controller;

import model.User;
import service.*;

public interface Controller {
    void setUserService(UserService userService);
    void setAuthorService(AuthorService authorService);
    void setConferenceSessionService(ConferenceSessionService conferenceSessionService);
    void setReviewerService(ReviewerService reviewerService);
    void setSectionService(SectionService sectionService);
    void setPaperService(PaperService paperService);
    void setAbstractService(AbstractService abstractService);
    void setCurrentUser(User user);
}
