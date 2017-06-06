package controller.impl;

import controller.AuthorWindowController;
import controller.Controller;
import controller.ListenerWindowController;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;
import service.*;
import util.AlertUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DefaultListenerWindowController implements ListenerWindowController, Controller {
    private AbstractService abstractService;
    private UserService userService;
    private AuthorService authorService;
    private PaperService paperService;
    private ReviewerService reviewerService;
    private SectionService sectionService;
    private ConferenceSessionService conferenceSessionService;
    private User user;
    private Author author;
    private DefaultAuthenticationController autentificationController;
    private boolean accepted;

    private ToggleGroup toggleGroupCardType  = new ToggleGroup();

    @FXML
    private TableView<Section> sectionsTable;

    @FXML
    private TableColumn<Section,String> colSection;

    @FXML
    private AnchorPane centerPane;

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
    public void initialize() {
        colSection.setCellValueFactory(new PropertyValueFactory<Section,String>("name"));
        toggleGroupCardType = new ToggleGroup();
        maestroRadioButton.setToggleGroup(toggleGroupCardType);
        mastercardRadioButton.setToggleGroup(toggleGroupCardType);
        visaRadioButton.setToggleGroup(toggleGroupCardType);
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
        List<Section> sections=new ArrayList<>((sectionService.getAll()));
        //System.out.println("seize" + sections.size());
        ObservableList<Section> observableList1 = FXCollections.observableList(sections);
        sectionsTable.setItems(observableList1);
    }

    public void restorePay(){

        centerPane.setVisible(true);
        centerPane.setVisible(false);
    }
    public void handlePay(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/content/ListenerView/PayView.fxml"));

        AnchorPane pane = null;
        try {
            fxmlLoader.setController(this);
            pane = fxmlLoader.load();
            centerPane.getChildren().clear();
            centerPane.getChildren().add(pane);
            centerPane.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
        }

        monthChoiceBox.getItems().setAll("Select","1","2","3","4","5","6","7","8","9","10","11","12");
        monthChoiceBox.setValue("Select");
        yearChoiceBox.getItems().setAll("2018","2017","2016","2015","2014","2013","2012","2011","2010","2009","2008","2007","2006");
        yearChoiceBox.setValue("2017");


    }
    public void handleRegister(){
        if(sectionsTable.getSelectionModel().getSelectedItem()==null){
            AlertUtil.showAlertMessage(Alert.AlertType.ERROR,"Please select a section!");
            return;
        }
        else{

            String s = sectionsTable.getSelectionModel().getSelectedItem().getName();
            //save to db ++
            AlertUtil.showAlertMessage(Alert.AlertType.CONFIRMATION,"Done!");
        }
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
        else
            restore();

        return error;

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
        centerPane.setVisible(false);
        AlertUtil.showAlertMessage(Alert.AlertType.CONFIRMATION,"Done!");
    }

    @FXML
    public void handleLogout() {


        Stage primaryStage=new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/components/Authentication.fxml"));
        BorderPane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Controller controller = loader.getController();
        setControllerServices(controller);
        Scene scene = new Scene( pane );
        primaryStage.setScene(scene);
        primaryStage.show();

        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();


    }

    public void setControllerServices(Controller controller){
        controller.setAuthorService(authorService);
        controller.setPaperService(paperService);
        controller.setAbstractService(abstractService);
        controller.setConferenceSessionService(conferenceSessionService);
        controller.setReviewerService(reviewerService);
        controller.setSectionService(sectionService);
        controller.setUserService(userService);
    }
}
