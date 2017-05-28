package controller.impl;

import controller.Controller;
import controller.StaffWindowController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import service.*;

import javax.swing.text.html.ListView;


public class DefaultStaffWindowController implements StaffWindowController, Controller {

    private UserService userService;
    private AuthorService authorService;
    private PaperService paperService;
    private ReviewerService reviewerService;
    private SectionService sectionService;
    private ConferenceSessionService conferenceSessionService;

    @FXML
    private ListView listViewConferenceComponents;

    @FXML
    private TableView tableViewConferenceComponentsWithInformations;

    @FXML
    private ListView listViewNotification;

    @FXML
    private Button buttonAssignPaperToReviewer;

    @FXML
    private Button buttonRequestToConference;

    @FXML
    private Button buttonChangeDeadline;

    @FXML
    private Button buttonChangeReviewer;

    public DefaultStaffWindowController() {
    }

    @FXML
    public void initialize()
    {

    }



    @Override
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public void setConferenceSessionService(ConferenceSessionService conferenceSessionService) {
        this.conferenceSessionService = conferenceSessionService;
    }

    @Override
    public void setReviewerService(ReviewerService reviewerService) {
        this.reviewerService = reviewerService;
    }

    @Override
    public void setSectionService(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @Override
    public void setPaperService(PaperService paperService) {
        this.paperService = paperService;
    }

    @Override
    public void handleChangeDeadline() {

    }
}
