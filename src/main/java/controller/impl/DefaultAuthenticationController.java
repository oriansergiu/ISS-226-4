package controller.impl;

import controller.AuthenticationController;
import controller.Controller;
import enums.UserRole;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.User;
import service.*;
import util.AlertUtil;
import util.UserUtil;
import validator.exceptions.UserException;

import java.io.IOException;

public class DefaultAuthenticationController implements AuthenticationController, Controller {
    private UserService userService;
    private AuthorService authorService;

    private ConferenceSessionService conferenceSessionService;

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
            case (2):
            {
                openWindow("Reviewer");
            }
            case (3):
            {
                openWindow("Staff");
            }
        }
    }

    private void openWindow(String userView) {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(DefaultAuthenticationController.class.getResource("/views/components/"+userView+"Window.fxml"));
        BorderPane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Controller controller = loader.getController();
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

    }

    public void setConferenceSessionService(ConferenceSessionService conferenceSessionService) {
        this.conferenceSessionService = conferenceSessionService;
    }

    @Override
    public void setReviewerService(ReviewerService reviewerService) {

    }

    @Override
    public void setSectionService(SectionService sectionService) {

    }

    @Override
    public void setPaperService(PaperService paperService) {

    }
}
