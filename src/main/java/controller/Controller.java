package controller;

import service.*;

/**
 * Created by Sergiu on 5/22/2017.
 */
public interface Controller {
    void setUserService(UserService userService);
    void setAuthorService(AuthorService authorService);
    void setConferenceSessionService(ConferenceSessionService conferenceSessionService);
    void setReviewerService(ReviewerService reviewerService);
    void setSectionService(SectionService sectionService);
    void setPaperService(PaperService paperService);
}
