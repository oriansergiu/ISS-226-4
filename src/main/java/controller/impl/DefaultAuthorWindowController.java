package controller.impl;

import controller.AuthorWindowController;
import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Abstract;
import model.Author;
import model.Paper;
import model.User;
import service.*;

import javax.swing.text.html.ListView;
import java.io.IOException;
import java.util.ArrayList;
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
    private Author author;

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

    @FXML
    public TableView<Paper> proposedPapersTableView;
    @FXML
    public TableColumn<Paper,String> proposedColumn;
    @FXML
    public TableView<Paper> acceptedPapersTableView;
    @FXML
    public TableColumn<Paper,String> acceptedColumn;


    public DefaultAuthorWindowController() {
    }

    @FXML
    public void initialize()
    {
        proposedColumn.setCellValueFactory(new PropertyValueFactory<Paper, String>("title"));
        acceptedColumn.setCellValueFactory(new PropertyValueFactory<Paper, String>("title"));
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

        this.author = authorService.getAuthorByUserId(user);

        if(author != null){
            List<Paper> proposedPapers = new ArrayList<>(author.getProposedPapers());
            ObservableList<Paper> observableList = FXCollections.observableList(proposedPapers);
            proposedPapersTableView.setItems(observableList);

            List<Paper> acceptedPapers = new ArrayList<>(author.getAcceptedPapers());
            ObservableList<Paper> observableList2 = FXCollections.observableList(acceptedPapers);
            acceptedPapersTableView.setItems(observableList2);
        }

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
        //Author thisAuthor = authorService.getAuthorByUserId(user);
        if(author == null){
            author = new Author();
            author.setUser(user);
            authorService.addAuthor(author);
        }

        Paper paper = new Paper();
        paper.setAuthor(author);
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

        List<Paper> proposedPapers = author.getProposedPapers();
        proposedPapers.add(paper);
        author.setProposedPapers(proposedPapers);
        authorService.updateAuthor(author);
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

    @FXML
    public void handleSelectionChanged(){
        Paper paper = proposedPapersTableView.getSelectionModel().getSelectedItem();
        System.out.println(paper);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/content/AuthorView/AddPaper.fxml"));
        AnchorPane pane = null;
        try {
            fxmlLoader.setController(this);
            pane = fxmlLoader.load();
            centerPane.getChildren().clear();
            centerPane.getChildren().add(pane);
            setModifyView(paper);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setModifyView(Paper paper){
        TitleTF.setText(paper.getTitle());
        KeywordsTF.setText(paper.getKeywords());
        TopicsTF.setText(paper.getTopic());
        coAuthorsTF.setText(paper.getCoAuthors());
        abstractTA.setText(paper.getPaperAbstract().getText());
        TitleTF.setEditable(false);
        KeywordsTF.setEditable(false);
        TopicsTF.setEditable(false);
        coAuthorsTF.setEditable(false);
        addPaperBtn.setText("Modify paper");
        addPaperBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                handleModifyPaper(paper);
            }
        });
    }

    private void handleModifyPaper(Paper paper) {
        Abstract _abstract = paper.getPaperAbstract();
        _abstract.setText(abstractTA.getText());
        abstractService.updateAbstract(_abstract);
    }


}