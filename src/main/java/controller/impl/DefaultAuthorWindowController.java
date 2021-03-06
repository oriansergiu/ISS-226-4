package controller.impl;

import controller.AuthorWindowController;
import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class DefaultAuthorWindowController implements AuthorWindowController, Controller {

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
    public Button showAddPaperBtn;

    @FXML
    public TitledPane proposedPapersTP;


    @FXML
    public Button uploadPaperBtn;

    @FXML
    public Button addPaperBtn;

    @FXML
    public TextField TitleTF;

    @FXML
    public TextField KeywordsTF;
    @FXML
    public TextField TopicsTF;
    @FXML
    public TextField coAuthorsTF;
    @FXML
    public TextArea abstractTA;
    @FXML
    public AnchorPane centerPane;
    @FXML
    public TextField filePathTF;
    @FXML
    public TableView<Paper> proposedPapersTableView;
    @FXML
    public TableColumn<Paper,String> proposedColumn;
    @FXML
    public TableView<Paper> acceptedPapersTableView;
    @FXML
    public TableColumn<Paper,String> acceptedColumn;
    @FXML
    private Button logoutButton;


    public DefaultAuthorWindowController() {
    }

    @FXML
    public void initialize()
    {
        colSection.setCellValueFactory(new PropertyValueFactory<Section,String>("name"));

        proposedColumn.setCellValueFactory(new PropertyValueFactory<Paper, String>("title"));
        acceptedColumn.setCellValueFactory(new PropertyValueFactory<Paper, String>("title"));

        // Toggle group for pay section
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

        this.author = authorService.getAuthorByUserId(user);

        if(author != null){
            List<Paper> proposedPapers = new ArrayList<>(author.getProposedPapers());
            ObservableList<Paper> observableList = FXCollections.observableList(proposedPapers);
            proposedPapersTableView.setItems(observableList);

            List<Paper> acceptedPapers = new ArrayList<>(author.getAcceptedPapers());
            ObservableList<Paper> observableList2 = FXCollections.observableList(acceptedPapers);
            acceptedPapersTableView.setItems(observableList2);
        }

    }

    @FXML
    public void handleAddPaper() {
        String title = this.TitleTF.getText();
        String coAuthors = this.coAuthorsTF.getText();
        String keywords = this.KeywordsTF.getText();
        String topic = this.TopicsTF.getText();
        String _abstractText = this.abstractTA.getText();
        byte[] attachment = null;
        Abstract _abstract = new Abstract();
        _abstract.setText(_abstractText);


        if(!filePathTF.getText().equals("")){
            Path path = Paths.get(filePathTF.getText());
            try {
                attachment =  Files.readAllBytes(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(author == null){
            author = new Author();
            author.setUser(user);
            authorService.addAuthor(author);
        }

        Byte[] byteObjects = new Byte[attachment.length];
        int i=0;
        for(byte b: attachment)
            byteObjects[i++] = b;  // Autoboxing.

        Paper paper = new Paper();
        paper.setAuthor(author);
        paper.setCoAuthors(coAuthors);
        paper.setKeywords(keywords);
        paper.setTitle(title);
        paper.setTopic(topic);
        paper.setAttachment(byteObjects);
        Abstract _abstractObj = new Abstract();
        _abstractObj.setText(_abstractText);

        abstractService.addAbstract(_abstractObj);
        paperService.addPaper(paper);
        _abstractObj.setPaper(paper);
        paper.setPaperAbstract(_abstractObj);
        abstractService.updateAbstract(_abstractObj);
        paperService.updatePaper(paper);

        List<Paper> proposedPapers = author.getProposedPapers();
        if(proposedPapers == null){
            proposedPapers = new ArrayList<>();
        }
        proposedPapers.add(paper);
        author.setProposedPapers(proposedPapers);
        authorService.updateAuthor(author);

        //proposedPapers = new ArrayList<>(author.getProposedPapers());
        ObservableList<Paper> observableList = FXCollections.observableList(proposedPapers);
        proposedPapersTableView.setItems(observableList);

    }

    @FXML
    public void handleProposedPapersClicked() {
        restorePay();
    }

    @FXML
    public void handleShowAddPaperBtn(){
        centerPane.setVisible(true);
        if(user.getSession().getAbstractDeadline().compareTo(new Date()) <= 0)
        {
            AlertUtil.showAlertMessage(Alert.AlertType.WARNING, "The deadline for new papers passed");
        }
        else{
            System.out.println("handle show add paper");
            System.out.println(user);
            System.out.println(authorService);
            System.out.println(this);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/content/AuthorView/AddPaper.fxml"));

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

    @FXML
    private TableView<Section> sectionsTable;

    @FXML
    private TableColumn<Section,String> colSection;



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
    public void restorePay(){

        centerPane.setVisible(true);
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
        centerPane.setVisible(false);

//        if(userService == null)
//            System.out.println("null");
//        else
//            System.out.println("not null");
//        //userService.update(user);
    }

    @FXML
    public void handleSelectionChanged(){
        Paper paper = proposedPapersTableView.getSelectionModel().getSelectedItem();
        if(paper == null)
            return;
        System.out.println(paper);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/content/AuthorView/AddPaper.fxml"));
        AnchorPane pane = null;
        try {
            fxmlLoader.setController(this);
            pane = fxmlLoader.load();
            centerPane.getChildren().clear();
            centerPane.getChildren().add(pane);
            accepted=false;
            setModifyView(paper);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void handleAcceptedSelectionChanged(){
        Paper paper = acceptedPapersTableView.getSelectionModel().getSelectedItem();
        if(paper == null)
            return;
        System.out.println(paper);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/content/AuthorView/AddPaper.fxml"));
        AnchorPane pane = null;
        try {
            fxmlLoader.setController(this);
            pane = fxmlLoader.load();
            centerPane.getChildren().clear();
            centerPane.getChildren().add(pane);
            accepted = true;
            setModifyView(paper);
            abstractTA.setEditable(false);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    public void handleUploadPaper(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("WORD files (*.doc)", "*.doc"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("WORD files (*.docx)", "*.docx"));

        File file = fileChooser.showOpenDialog(null);
        if(file == null){
        }
        else{
            filePathTF.setText(file.getAbsolutePath());
        }
    }

    public void setModifyView(Paper paper){
        if(paper == null)
            return;
        TitleTF.setText(paper.getTitle());
        KeywordsTF.setText(paper.getKeywords());
        TopicsTF.setText(paper.getTopic());
        coAuthorsTF.setText(paper.getCoAuthors());
        abstractTA.setText(paper.getPaperAbstract().getText());
        TitleTF.setEditable(false);
        KeywordsTF.setEditable(false);
        TopicsTF.setEditable(false);
        coAuthorsTF.setEditable(false);
        if(user.getSession().getAbstractDeadline().compareTo(new Date()) <= 0)
        {
            abstractTA.setEditable(false);
        }
//        Date today = new Date();
        Timestamp today = new Timestamp(System.currentTimeMillis());
        if((user.getSession().getProposalDeadline().before(today) && !accepted)){
                uploadPaperBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        AlertUtil.showAlertMessage(Alert.AlertType.WARNING, "The deadline for proposal passed");
                    }
                });
        }
        if(user.getSession().getStartDate().before(today)){
            uploadPaperBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    AlertUtil.showAlertMessage(Alert.AlertType.WARNING, "The deadline for proposal passed");
                }
            });
        }
        addPaperBtn.setText("Modify paper");
        addPaperBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                handleModifyPaper(paper);
            }
        });
    }

    private void handleModifyPaper(Paper paper) {
        Abstract _abstract = paper.getPaperAbstract();
        _abstract.setText(abstractTA.getText());
        byte[] attachment = null;
        if(!filePathTF.getText().equals("")){
            Path path = Paths.get(filePathTF.getText());
            try {
                attachment =  Files.readAllBytes(path);
                Byte[] byteObjects = new Byte[attachment.length];
                int i=0;
                for(byte b: attachment)
                    byteObjects[i++] = b;  // Autoboxing.
                paper.setAttachment(byteObjects);

                paperService.updatePaper(paper);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        abstractService.updateAbstract(_abstract);
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
