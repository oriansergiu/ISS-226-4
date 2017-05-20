import controller.AuthenticationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import repository.ConferenceSessionRepository;
import repository.UserRepository;
import repository.impl.DefaultConferenceSessionRepository;
import repository.impl.DefaultUserRepository;
import service.ConferenceSessionService;
import service.UserService;
import service.impl.DefaultConferenceSessionService;
import service.impl.DefaultUserService;

import java.io.IOException;

/**
 * Created by raulp on 5/20/2017.
 */
public class App extends Application {
    private UserRepository userRepository = new DefaultUserRepository();

    private UserService userService = new DefaultUserService();

    private ConferenceSessionRepository conferenceSessionRepository = new DefaultConferenceSessionRepository();

    private ConferenceSessionService conferenceSessionService = new DefaultConferenceSessionService();

    private AuthenticationController authenticationController;

    public static void main(String[] args) {


        launch(args);
    }

    private void setRepositoriesToServices() {
        userService.setUserRepository(userRepository);
        conferenceSessionService.setConferenceSessionRepository(conferenceSessionRepository);
    }

    private void setServicesToControllers() {
        authenticationController.setUserService(userService);
        authenticationController.setConferenceSessionService(conferenceSessionService);
    }

    private void loadAuthenticationView(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/views/components/Authentication.fxml"));
        BorderPane pane = loader.load();
        authenticationController = loader.getController();
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void start(Stage primaryStage) throws Exception {
        //Firstly load the controllers from the fxml files
        loadAuthenticationView(primaryStage);

        //Then set the repositories to services
        setRepositoriesToServices();

        //Then set services to controllers
        setServicesToControllers();
    }
    //just a test
}
