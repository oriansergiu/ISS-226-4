package controller.impl;

import controller.AuthenticationController;
import enums.UserRole;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import model.User;
import service.ConferenceSessionService;
import service.UserService;
import util.AlertUtil;
import util.UserUtil;
import validator.exceptions.UserException;

public class DefaultAuthenticationController implements AuthenticationController {
    private UserService userService;

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
            userService.loginUser(textFieldLoginEmail.getText(), textFieldLoginPassword.getText());
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

        }
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setConferenceSessionService(ConferenceSessionService conferenceSessionService) {
        this.conferenceSessionService = conferenceSessionService;
    }
}
