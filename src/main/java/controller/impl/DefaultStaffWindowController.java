package controller.impl;

import com.mysql.cj.api.Session;
import controller.Controller;
import controller.StaffWindowController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Conference;
import model.ConferenceSession;
import model.Paper;
import model.User;
import service.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.text.html.ListView;


public class DefaultStaffWindowController implements StaffWindowController, Controller {

    private UserService userService;
    private AuthorService authorService;
    private User user;
    private PaperService paperService;
    private ReviewerService reviewerService;
    private SectionService sectionService;
    private AbstractService abstractService;
    private ConferenceSessionService conferenceSessionService;

    @FXML
    private ListView listViewConferenceComponents;

    @FXML
    private TableView tableViewConferenceComponentsWithInformations;

    @FXML
    TableView<ConferenceSession> sessionTableView;

    @FXML
    public TableColumn<ConferenceSession, Date> sessionsColumn;

    @FXML
    public AnchorPane centerPane;

    @FXML
    public TextField abstractDeadline;

    @FXML
    public TextField proposalDeadline;

    @FXML
    private Button deadlineButton;


    public DefaultStaffWindowController() {
    }

    @FXML
    public void initialize()
    {
        sessionsColumn.setCellValueFactory(new PropertyValueFactory<ConferenceSession,Date>("startDate"));
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

        System.out.println(conferenceSessionService);
        List<ConferenceSession> conferenceSessions = new ArrayList<>(conferenceSessionService.getAll());
            ObservableList<ConferenceSession> observableList = FXCollections.observableList(conferenceSessions);
            sessionTableView.setItems(observableList);
    }

    @FXML
    public void handleSelectionChanged(){
        ConferenceSession session = sessionTableView.getSelectionModel().getSelectedItem();
        System.out.println(session);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/content/StaffView/PustponeDeadline.fxml"));
        AnchorPane pane = null;
        try {
            fxmlLoader.setController(this);
            pane = fxmlLoader.load();
            centerPane.getChildren().clear();
            centerPane.getChildren().add(pane);
            setModifyView(session);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setModifyView(ConferenceSession session){
        abstractDeadline.setText(String.valueOf(session.getAbstractDeadline()));
        proposalDeadline.setText(String.valueOf(session.getProposalDeadline()));


        deadlineButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
               // handleModifyPaper(paper);
            }
        });
    }



    @FXML
   public void handleChangeDeadline() {

        String abstractDeadline = this.abstractDeadline.getText();
        String proposalDeadline = this.proposalDeadline.getText();

        Date abs= null;
        Date prop=null;
        try {
            abs = new SimpleDateFormat("dd/MM/yyyy").parse(abstractDeadline);
            prop=new SimpleDateFormat("dd/MM/yyyy").parse(proposalDeadline);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ConferenceSession conferenceSession = new ConferenceSession();
        conferenceSession.setAbstractDeadline(abs);
        conferenceSession.setProposalDeadline(prop);

        conferenceSessionService.updateConferenceSession(conferenceSession);
   }



}
