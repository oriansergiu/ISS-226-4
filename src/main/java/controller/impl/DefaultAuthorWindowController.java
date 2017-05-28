package controller.impl;

import controller.AuthorWindowController;
import controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.Abstract;
import model.Author;
import model.Paper;
import model.User;
import service.*;

import javax.swing.text.html.ListView;
import java.io.IOException;
import java.util.List;


public class DefaultAuthorWindowController implements AuthorWindowController, Controller {

    private AbstractService abstractService;
    private UserService userService;
    private AuthorService authorService;
    private PaperService paperService;
    private ReviewerService reviewerService;
    private SectionService sectionService;
    private ConferenceSessionService conferenceSessionService;
    private User user;
    @FXML
    public ListView proposedPapersList;

    @FXML
    public ListView acceptedPapersList;

    @FXML
    public Button showAddPaperBtn;

    @FXML
    public TitledPane proposedPapersTP;

    @FXML
    public Button uploadPaperBtn;

    @FXML
    public Button addPaperBtn;

    @FXML
    public TextField TitleTF;

    @FXML
    public TextField KeywordsTF;
    @FXML
    public TextField TopicsTF;
    @FXML
    public TextField coAuthorsTF;
    @FXML
    public TextArea abstractTA;
    @FXML
    public AnchorPane centerPane;

    public DefaultAuthorWindowController() {
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
    public void setAbstractService(AbstractService abstractService) {
        this.abstractService = abstractService;
    }

    @Override
    public void setCurrentUser(User user) {
        this.user = user;
    }

    @FXML
    public void handleAddPaper() {
        String title = this.TitleTF.getText();
        String coAuthors = this.coAuthorsTF.getText();
        String keywords = this.KeywordsTF.getText();
        String topic = this.TopicsTF.getText();
        String _abstractText = this.abstractTA.getText();

        Abstract _abstract = new Abstract();
        _abstract.setText(_abstractText);

        //Author thisAuthor = ;
        Author thisAuthor = authorService.getAuthorByUserId(user);
        if(thisAuthor == null){
            thisAuthor = new Author();
            thisAuthor.setUser(user);
            authorService.addAuthor(thisAuthor);
        }

        Paper paper = new Paper();
        paper.setAuthor(thisAuthor);
        paper.setCoAuthors(coAuthors);
        paper.setKeywords(keywords);
        paper.setTitle(title);
        paper.setTopic(topic);

        Abstract _abstractObj = new Abstract();
        _abstractObj.setText(_abstractText);

        abstractService.addAbstract(_abstractObj);
        paperService.addPaper(paper);
        _abstractObj.setPaper(paper);
        paper.setPaperAbstract(_abstractObj);
        abstractService.updateAbstract(_abstractObj);
        paperService.updatePaper(paper);

        List<Paper> proposedPapers = thisAuthor.getProposedPapers();
        proposedPapers.add(paper);
        thisAuthor.setProposedPapers(proposedPapers);
        authorService.updateAuthor(thisAuthor);
    }

    @FXML
    public void handleProposedPapersClicked() {
    }

    @FXML
    public void handleShowAddPaperBtn(){
        System.out.println("handle show add paper");
        System.out.println(user);
        System.out.println(authorService);
        System.out.println(this);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/content/AuthorView/AddPaper.fxml"));

        AnchorPane pane = null;
        try {
            fxmlLoader.setController(this);
            pane = fxmlLoader.load();
            centerPane.getChildren().clear();
            centerPane.getChildren().add(pane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setControllerServices(Controller controller){
        controller.setAuthorService(authorService);
        controller.setPaperService(paperService);
    }

}
