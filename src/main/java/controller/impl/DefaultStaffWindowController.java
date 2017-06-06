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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.*;
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
    TableView<Section> sectionTableView;

    @FXML
    public TableColumn<ConferenceSession, Date> sessionsColumn;

    @FXML
    public TableColumn<Section, String> sectionsColumn;

    @FXML
    public AnchorPane centerPane;

    @FXML
    public TextField abstractDeadline;

    @FXML
    public TextField proposalDeadline;


    @FXML
    public TextField name;

    @FXML
    public TextField description;

    @FXML
    private Button logoutButton;
    @FXML
    private Button addSectionBtn;

    public DefaultStaffWindowController() {
    }

    @FXML
    public void initialize()
    {
        sessionsColumn.setCellValueFactory(new PropertyValueFactory<ConferenceSession,Date>("startDate"));
        sectionsColumn.setCellValueFactory(new PropertyValueFactory<Section, String>("name"));
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

        //System.out.println(conferenceSessionService);
        List<ConferenceSession> conferenceSessions = new ArrayList<>(conferenceSessionService.getAll());
            ObservableList<ConferenceSession> observableList = FXCollections.observableList(conferenceSessions);
            sessionTableView.setItems(observableList);

        //System.out.println(sectionService);
        List<Section> sections=new ArrayList<>((sectionService.getAll()));

        ObservableList<Section> observableList1 = FXCollections.observableList(sections);
        sectionTableView.setItems(observableList1);
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
    }



    @FXML
   public void handleChangeDeadline() {

        String abstractDeadline = this.abstractDeadline.getText();
        String proposalDeadline = this.proposalDeadline.getText();

        ConferenceSession session = sessionTableView.getSelectionModel().getSelectedItem();
        System.out.println(this.proposalDeadline.getText());

        Date abs= null;
        Date prop=null;
        try {
            abs = new SimpleDateFormat("yyyy-MM-dd").parse(abstractDeadline);
            prop=new SimpleDateFormat("yyyy-MM-dd").parse(proposalDeadline);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println(prop);

        session.setAbstractDeadline(abs);
        session.setProposalDeadline(prop);
        conferenceSessionService.updateConferenceSession(session);
        this.abstractDeadline.setText("");
        this.proposalDeadline.setText("");

   }
    @FXML
    public void handleAddSection() {


        String name = this.name.getText();
        String description = this.description.getText();


        Section section = new Section();
        section.setName(name);
        section.setDescription(description);
        section.setPapers(null);
        section.setUser(user);


        sectionService.addSection(section);
        List<Section> sections=new ArrayList<>((sectionService.getAll()));
        ObservableList<Section> observableList = FXCollections.observableList(sections);
        sectionTableView.setItems(observableList);
        this.name.setText("");
        this.description.setText("");

    }

    @FXML
    public void handleSelectionAdd(){
        Section section = sectionTableView.getSelectionModel().getSelectedItem();
        System.out.println(section);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/content/StaffView/AddSection.fxml"));
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


    @FXML
    public void handleShowAddSectionBtn(){


           // System.out.println("handle show add paper");
          //  System.out.println(user);
          //  System.out.println(authorService);
         //   System.out.println(this);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/content/StaffView/AddSection.fxml"));

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
}
