package controller.impl;

import com.sun.prism.paint.Color;
import controller.AuthenticationController;
import controller.Controller;
import enums.UserRole;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import model.User;
import service.*;
import util.AlertUtil;
import util.UserUtil;
import validator.exceptions.UserException;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Objects;

public class DefaultAuthenticationController implements AuthenticationController, Controller {
    private UserService userService;
    private AuthorService authorService;
    private PaperService paperService;
    private AbstractService abstractService;
    private ConferenceSessionService conferenceSessionService;
    private ReviewerService reviewerService;
    private SectionService sectionService;


    @FXML
    private RadioButton radioButtonRegisterUserRoleListener;

    @FXML
    private RadioButton radioButtonRegisterUserRoleAuthor;

    @FXML
    private RadioButton radioButtonRegisterUserRoleReviewer;

    @FXML
    private RadioButton radioButtonRegisterUserRoleStaff;

    @FXML
    private TextField textFieldRegisterFirstName;

    @FXML
    private TextField textFieldRegisterLastName;

    @FXML
    private TextField textFieldRegisterEmail;

    @FXML
    private TextField textFieldRegisterPassword;

    @FXML
    private TextField textFieldRegisterPasswordRepeat;

    @FXML
    private TextField textFieldRegisterAffiliation;

    @FXML
    private TextField textFieldLoginEmail;

    @FXML
    private TextField textFieldLoginPassword;

    @FXML
     private Button exitButton ;

    ToggleGroup toggleGroupUserRole;

    public DefaultAuthenticationController() {
    }

    @FXML
    public void initialize() {
        toggleGroupUserRole = new ToggleGroup();
        radioButtonRegisterUserRoleListener.setToggleGroup(toggleGroupUserRole);
        radioButtonRegisterUserRoleAuthor.setToggleGroup(toggleGroupUserRole);
        radioButtonRegisterUserRoleReviewer.setToggleGroup(toggleGroupUserRole);
        radioButtonRegisterUserRoleStaff.setToggleGroup(toggleGroupUserRole);
    }

    public int verifyLogin(){
        Integer error = 0;
        if(Objects.equals(textFieldLoginEmail.getText(), "")){
            textFieldLoginEmail.setStyle("-fx-control-inner-background: #5ef4d2");
            error = 1;
        }
        else{
            textFieldLoginEmail.setStyle("-fx-control-inner-background: #FFFFFF");
        }
        if(Objects.equals(textFieldLoginPassword.getText(), "")){
            textFieldLoginPassword.setStyle("-fx-control-inner-background: #5ef4d2");
            error = 1;
        }
        else{
            textFieldLoginPassword.setStyle("-fx-control-inner-background: #FFFFFF");
        }
        return error;

    }
    public void handleLogin() {
        if(verifyLogin() == 1)
            return;
        try {
            User user = userService.loginUser(textFieldLoginEmail.getText(), textFieldLoginPassword.getText());
            loadMainWindow(user);
        } catch (UserException e) {
            AlertUtil.showAlertMessage(Alert.AlertType.WARNING, e.getMessage());
        }
    }

    public int verify(){
        Integer error = 0;
        if(Objects.equals(textFieldRegisterFirstName.getText(), "")){
            textFieldRegisterFirstName.setStyle("-fx-control-inner-background: #5ef4d2");
            error =1;
        }
        else{
            textFieldRegisterFirstName.setStyle("-fx-control-inner-background: #FFFFFF");
        }

        if(Objects.equals(textFieldRegisterLastName.getText(), "")){
            textFieldRegisterLastName.setStyle("-fx-control-inner-background: #5ef4d2");
            error =1;
        }
        else{
            textFieldRegisterLastName.setStyle("-fx-control-inner-background: #FFFFFF");
        }
        if(Objects.equals(textFieldRegisterEmail.getText(), "")){
            textFieldRegisterEmail.setStyle("-fx-control-inner-background: #5ef4d2");
            error =1;
        }
        else{
            textFieldRegisterEmail.setStyle("-fx-control-inner-background: #FFFFFF");

        }

        if(Objects.equals(textFieldRegisterPassword.getText(), "")){
            textFieldRegisterPassword.setStyle("-fx-control-inner-background: #5ef4d2");
            error =1;
        }
        else{
            textFieldRegisterPassword.setStyle("-fx-control-inner-background: #FFFFFF");

        }

        if(Objects.equals(textFieldRegisterPasswordRepeat.getText(), "")){
            textFieldRegisterPasswordRepeat.setStyle("-fx-control-inner-background: #5ef4d2");
            error =1;
        }
        else{
            textFieldRegisterPasswordRepeat.setStyle("-fx-control-inner-background: #FFFFFF");

        }
        if(Objects.equals(textFieldRegisterAffiliation.getText(), "")){
            textFieldRegisterAffiliation.setStyle("-fx-control-inner-background: #5ef4d2");
            error =1;
        }
        else{
            textFieldRegisterAffiliation.setStyle("-fx-control-inner-background: #FFFFFF");

        }



    if(toggleGroupUserRole.getSelectedToggle() == null){
        AlertUtil.showAlertMessage(Alert.AlertType.ERROR,"Must select a type");
        error = 1;
    }
        return error;
    }

    public void handleRegister() {
        User user = new User();
        if(verify()==1)
            return;

        user.setFirstName(textFieldRegisterFirstName.getText());
        user.setLastName(textFieldRegisterLastName.getText());
        user.setEmail(textFieldRegisterEmail.getText());
        user.setPassword(UserUtil.hashPassword(textFieldRegisterPassword.getText()));
        user.setAffiliation(textFieldRegisterAffiliation.getText());
        user.setRegistrationFee(false);
        user.setSession(conferenceSessionService.findById(1));
        UserRole userRole = UserRole.LISTENER;

        RadioButton radioButton = (RadioButton) toggleGroupUserRole.getSelectedToggle();

        if (radioButton.getId().equals("radioButtonRegisterUserRoleListener")) {
            userRole = UserRole.LISTENER;
        }

        if (radioButton.getId().equals("radioButtonRegisterUserRoleAuthor")) {
            userRole = UserRole.AUTHOR;
        }

        if (radioButton.getId().equals("radioButtonRegisterUserRoleReviewer")) {
            userRole = UserRole.REVIEWER;
        }

        if (radioButton.getId().equals("radioButtonRegisterUserRoleStaff")) {
            userRole = UserRole.STAFF;
        }

        user.setUserRole((Integer) userRole.ordinal());

        try {
            userService.save(user);
        } catch (UserException e) {
            AlertUtil.showAlertMessage(Alert.AlertType.WARNING, e.getMessage());
        }
        restoreRegister();
    }

    public void loadMainWindow(User user) {
        switch (user.getUserRole()) {
            case (0):
            {
                openWindow("Listener",user);
                break;

            }
            case (1):
            {
                openWindow("Author",user);
                break;
            }
            case (2):
            {
                openWindow("Reviewer",user);
                break;
            }
            case (3):
            {
                openWindow("Staff",user);
                break;
            }

        }
    }

    private void openWindow(String userView,User user) {
        FXMLLoader loader = new FXMLLoader(DefaultAuthenticationController.class.getResource("/views/components/"+userView+"Window.fxml"));
        BorderPane pane = null;
        System.out.println(userView.toString());
        System.out.println(userView);
        Stage primaryStage = new Stage();
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Controller controller = loader.getController();
        setControllerServices(controller);
        setControllerCurrentUser(controller,user);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();

        Stage stage = (Stage) radioButtonRegisterUserRoleAuthor.getScene().getWindow();
        stage.close();

    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    public void setConferenceSessionService(ConferenceSessionService conferenceSessionService) {
        this.conferenceSessionService = conferenceSessionService;
    }

    @Override
    public void setReviewerService(ReviewerService reviewerService) {
        this.reviewerService = reviewerService;
    }

    @Override
    public void setSectionService(SectionService sectionService) {this.sectionService=sectionService;

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

    }

    public void setControllerServices(Controller controller){
        controller.setAuthorService(authorService);
        controller.setPaperService(paperService);
        controller.setAbstractService(abstractService);
        controller.setConferenceSessionService(conferenceSessionService);
        controller.setReviewerService(reviewerService);
        controller.setSectionService(sectionService);
    }


    public void setControllerCurrentUser(Controller controller,User user){
        controller.setCurrentUser(user);
    }


    public void restoreRegister(){
        textFieldRegisterFirstName.setText("");
        textFieldRegisterLastName.setText("");
        textFieldRegisterAffiliation.setText("");
        textFieldRegisterEmail.setText("");
        textFieldRegisterPassword.setText("");
        textFieldRegisterPasswordRepeat.setText("");
        toggleGroupUserRole.selectToggle(null);
    }



    public void handleExit() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
