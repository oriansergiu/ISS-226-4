import controller.AuthenticationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Paper;
import repository.*;
import repository.impl.*;
import service.*;
import service.impl.*;

import java.io.IOException;

public class App extends Application {
    private AbstractRepository abstractRepository = new DefaultAbstractRepository();
    private AbstractService abstractService = new DefaultAbstractService();

    private UserRepository userRepository = new DefaultUserRepository();
    private UserService userService = new DefaultUserService();

    private PaperRepository paperRepository = new DefaultPaperRepository();
    private PaperService paperService = new DefaultPaperService();


    private AuthorRepository authorRepository = new DefaultAuthorRepository();
    private AuthorService authorService = new DefaultAuthorService();

    private ConferenceSessionRepository conferenceSessionRepository = new DefaultConferenceSessionRepository();
    private ConferenceSessionService conferenceSessionService = new DefaultConferenceSessionService();

    private AuthenticationController authenticationController;

    public static void main(String[] args) {
        launch(args);
    }

    private void setRepositoriesToServices() {
        userService.setUserRepository(userRepository);
        conferenceSessionService.setConferenceSessionRepository(conferenceSessionRepository);
        paperService.setPaperRepository(paperRepository);
        authorService.setAuthorRepository(authorRepository);
    }

    private void setServicesToControllers() {
        authenticationController.setUserService(userService);
        authenticationController.setConferenceSessionService(conferenceSessionService);
        authenticationController.setAuthorService(authorService);
        authenticationController.setPaperService(paperService);
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
}
