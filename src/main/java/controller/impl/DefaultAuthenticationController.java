package controller.impl;

import controller.AuthenticationController;
import controller.Controller;
import enums.UserRole;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.User;
import service.*;
import util.AlertUtil;
import util.UserUtil;
import validator.exceptions.UserException;

import java.awt.event.ActionEvent;
import java.io.IOException;

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
    private TextField textFieldRegisterRepeatPassword;

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

    public void handleLogin() {
        try {
            User user = userService.loginUser(textFieldLoginEmail.getText(), textFieldLoginPassword.getText());
            loadMainWindow(user);
        } catch (UserException e) {
            AlertUtil.showAlertMessage(Alert.AlertType.WARNING, e.getMessage());
        }
    }

    public void handleRegister() {
        User user = new User();

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
    }

    public void loadMainWindow(User user) {
        switch (user.getUserRole()) {
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



    public void handleExit() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
