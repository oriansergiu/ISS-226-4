import controller.AuthenticationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import repository.*;
import repository.impl.*;
import service.*;
import service.impl.*;
import test.TestRepository.*;
import test.TestServices.TestUserService;

import java.io.IOException;

public class App extends Application {
    private ListenerRepository listenerRepository = new DefaultListenerRepository();
    private ListenerService listenerService = new DefaultListenerService();

    private AbstractRepository abstractRepository = new DefaultAbstractRepository();
    private AbstractService abstractService = new DefaultAbstractService();

    private UserRepository userRepository = new DefaultUserRepository();
    private UserService userService = new DefaultUserService();

    private SectionRepository sectionRepository = new DefaultSectionRepository();
    private SectionService sectionService = new DefaultSectionService();

    private PaperRepository paperRepository = new DefaultPaperRepository();
    private PaperService paperService = new DefaultPaperService();

    private AuthorRepository authorRepository = new DefaultAuthorRepository();
    private AuthorService authorService = new DefaultAuthorService();

    private ConferenceSessionRepository conferenceSessionRepository = new DefaultConferenceSessionRepository();
    private ConferenceSessionService conferenceSessionService = new DefaultConferenceSessionService();

    private ReviewerRepository reviewerRepository = new DefaultReviewerRepository();
    private ReviewerService reviewerService = new DefaultReviewerService();

    private AuthenticationController authenticationController;

    public static void main(String[] args) {
        launch(args);
    }

    private void setRepositoriesToServices() {
        listenerService.setListenerRepository(listenerRepository);
        userService.setUserRepository(userRepository);
        conferenceSessionService.setConferenceSessionRepository(conferenceSessionRepository);
        paperService.setPaperRepository(paperRepository);
        authorService.setAuthorRepository(authorRepository);
        abstractService.setAbstractRepository(abstractRepository);
        reviewerService.setReviewerRepository(reviewerRepository);
        sectionService.setSectionRepository(sectionRepository);
    }

    private void setServicesToControllers() {
        authenticationController.setUserService(userService);
        authenticationController.setConferenceSessionService(conferenceSessionService);
        authenticationController.setAuthorService(authorService);
        authenticationController.setPaperService(paperService);
        authenticationController.setAbstractService(abstractService);
        authenticationController.setReviewerService(reviewerService);
        authenticationController.setSectionService(sectionService);
    }

    private void loadAuthenticationView(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/views/components/Authentication.fxml"));
        BorderPane pane = loader.load();
        authenticationController = loader.getController();
       // authenticationController.setMainApplic(this);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void start(Stage primaryStage) throws Exception {

        //TESTS
        tests();
        System.out.println("TESTS PASSED");

        //Firstly load the controllers from the fxml files
        loadAuthenticationView(primaryStage);

        //Then set the repositories to services
        setRepositoriesToServices();

        //Then set services to controllers
        setServicesToControllers();
    }

    public void tests()
    {
        TestUserService testUserService = new TestUserService();
        TestAbstractRepository abstractRepository = new TestAbstractRepository();
        TestAuthorRepository authorRepository = new TestAuthorRepository();
        TestConferenceRepository conferenceRepository = new TestConferenceRepository();
        TestConferenceSessionRepository conferenceSessionRepository = new TestConferenceSessionRepository();
        TestPaperRepository paperRepository = new TestPaperRepository();
        TestReviewerRepository reviewerRepository = new TestReviewerRepository();
        TestSectionRepository sectionRepository = new TestSectionRepository();
        TestUserRepository userRepository = new TestUserRepository();

        testUserService.tests();
        abstractRepository.Test();
        authorRepository.Test();
        conferenceRepository.Test();
        conferenceSessionRepository.Test();
        paperRepository.Test();
        reviewerRepository.Test();
        sectionRepository.Test();
        userRepository.Test();
    }

}
