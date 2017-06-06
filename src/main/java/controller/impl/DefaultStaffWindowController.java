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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.*;
import service.*;
import util.AlertUtil;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    private ToggleGroup toggleGroupCardType  = new ToggleGroup();
    @FXML
    public TextField name;

    @FXML
    public TextField description;

    @FXML
    private ChoiceBox<String> monthChoiceBox;

    @FXML
    private ChoiceBox<String> yearChoiceBox;
    @FXML
    private RadioButton visaRadioButton = new RadioButton();
    @FXML
    private RadioButton mastercardRadioButton = new RadioButton();
    @FXML
    private RadioButton maestroRadioButton = new RadioButton();

    @FXML
    private TextField cvvCodeText;

    @FXML
    private TextField cardCodeText;

    @FXML
    private RadioButton termsRadioButton;

    @FXML
    private Button logoutButton;
    @FXML
    private Button addSectionBtn;

    public DefaultStaffWindowController() {
    }

    @FXML
    public void initialize()
    {

        toggleGroupCardType = new ToggleGroup();
        maestroRadioButton.setToggleGroup(toggleGroupCardType);
        mastercardRadioButton.setToggleGroup(toggleGroupCardType);
        visaRadioButton.setToggleGroup(toggleGroupCardType);

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

    public int verifyPay(){
        Integer error = 0;
        String msg = "";
        String month = monthChoiceBox.getValue();
        String year = yearChoiceBox.getValue();
        String cvv = cvvCodeText.getText();
        String code = cardCodeText.getText();

        if(Objects.equals(month, "Select")){
            error = 1;
            msg += "Please select the month\n";
        }

        if(Objects.equals(year, "")){
            error = 1;
            msg += "Please select the year\n";
        }

        if(Objects.equals(cvv, "")){
            error = 1;
            msg += "Please enter the cvv code\n";
        }
        Integer errorCvv = 0;
        for(int i=0;i<cvv.length();i++){
            if(!("0123456789".contains(cvv.substring(i,i+1)))){

                errorCvv=1;
            }

        }
        if(errorCvv == 1){
            error = 1;
            msg += "CVV must be only digits not letters \n";
        }
        Integer errorCode=0;
        for(int i=0;i<code.length();i++){
            if(!("0123456789".contains(code.substring(i,i+1)))){
                errorCode = 1;
            }

        }
        if(errorCode == 1){
            msg += "Code must be only digits not letters \n";
            error= 1;
        }

        if(Objects.equals(code, "")){
            error = 1;
            msg += "Please enter the card code\n";
        }

        if(!(termsRadioButton.isSelected())){
            error = 1;
            msg+= "You must accept the terms to continue\n";
        }


        if(toggleGroupCardType.getSelectedToggle() == null){
            msg+="You must select the card type\n";
            error = 1;
        }
        if(error==1){
            AlertUtil.showAlertMessage(Alert.AlertType.ERROR,msg);
        }
        else{
            AlertUtil.showAlertMessage(Alert.AlertType.CONFIRMATION,"Done!");
            restore();
        }


        return error;

    }

    public void handlePay(){
        centerPane.setVisible(true);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/content/ListenerView/PayView.fxml"));

        AnchorPane pane = null;
        try {
            fxmlLoader.setController(this);
            pane = fxmlLoader.load();
            centerPane.getChildren().clear();
            centerPane.getChildren().add(pane);

        } catch (IOException e) {
            e.printStackTrace();
        }

        monthChoiceBox.getItems().setAll("Select","1","2","3","4","5","6","7","8","9","10","11","12");
        monthChoiceBox.setValue("Select");
        yearChoiceBox.getItems().setAll("2018","2017","2016","2015","2014","2013","2012","2011","2010","2009","2008","2007","2006");
        yearChoiceBox.setValue("2017");
    }
    public void restorePay(){

        centerPane.setVisible(false);
    }
    private void restore() {
        cardCodeText.setText("");
        cvvCodeText.setText("");
        toggleGroupCardType.selectToggle(null);
        termsRadioButton.setSelected(false);
        monthChoiceBox.setValue("Select");
        yearChoiceBox.setValue("2017");
    }

    public void handleSubmit(){
        if(verifyPay() == 1){
            return;
        }
        user.setRegistrationFee(true);
        userService.update(user);
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
