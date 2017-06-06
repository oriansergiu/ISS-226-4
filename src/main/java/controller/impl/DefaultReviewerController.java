package controller.impl;

import controller.Controller;
import enums.AcceptanceType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.*;
import org.hibernate.Hibernate;
import service.*;
import util.AlertUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DefaultReviewerController implements Controller {
    private AbstractService abstractService;
    private UserService userService;
    private AuthorService authorService;
    private PaperService paperService;
    private ReviewerService reviewerService;
    private SectionService sectionService;
    private ConferenceSessionService conferenceSessionService;
    private User user;
    private Reviewer reviewer;

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
    public AnchorPane centerPane;

    @FXML
    public Label labelReviewerDateError;

    @FXML
    public TableView<Paper> tableViewBidPapers;

    @FXML
    public TableColumn<Paper,String> tableColumnBidPapersAllPapers;

    @FXML
    public TextArea textAreaBidPapersPaperAbstract;

    @FXML
    public TableView<Paper> tableViewBidPapersAdded;

    @FXML
    public TableColumn<Paper, String> tableColumnBidPapersAdded;

    @FXML
    public TableView<Paper> tableViewReviewPapers;

    @FXML
    public TableColumn<Paper, String> tableColumnReviewPapers;

    @FXML
    public TableView<Paper> tableViewReviewPapersReviewed;

    @FXML
    public TableColumn<Paper, String> tableColumnReviewPapersReviewed;

    @FXML
    public RadioButton radioButtonReviewPaperStrongReject;

    @FXML
    public RadioButton radioButtonReviewPaperReject;

    @FXML
    public RadioButton radioButtonReviewPaperWeakReject;

    @FXML
    public RadioButton radioButtonReviewPaperBorderline;

    @FXML
    public RadioButton radioButtonReviewPaperWeakAccept;

    @FXML
    public RadioButton radioButtonReviewPaperAccept;

    @FXML
    public RadioButton radioButtonReviewPaperStrongAccept;

    @FXML
    public TextArea textAreaReviewPapersComment;

    @FXML
    public ListView<String> listViewReviewedPapersAllVotes;

    @FXML
    private Button logoutButton;

    ToggleGroup toggleGroupAcceptanceType;

    private ObservableList<Paper> bidPapersAllPapersModel;
    private ObservableList<Paper> bidPapersAddedPapersModel;

    private ObservableList<Paper> reviewPapersAllPapersModel;
    private ObservableList<Paper> reviewPapersReviewedPapersModel;

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

    @Override
    public void setCurrentUser(User user) {
        this.user = user;

        List<Section> sections=new ArrayList<>((sectionService.getAll()));
        //System.out.println("seize" + sections.size());
        ObservableList<Section> observableList1 = FXCollections.observableList(sections);
        sectionsTable.setItems(observableList1);

        Reviewer reviewer = reviewerService.getReviewerByUser(user);

        if (reviewer == null) {
            reviewer = new Reviewer();
            reviewer.setUser(user);
            reviewerService.save(reviewer);
            reviewer = reviewerService.getReviewerByUser(user);
        }

        this.reviewer = reviewer;
    }

    private void loadMainView(String componentName) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/content/ReviewerView/" + componentName + ".fxml"));
        AnchorPane pane = null;

        try {
            loader.setController(this);
            pane = loader.load();
            centerPane.getChildren().clear();
            centerPane.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
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
    public void handleShowBinPapersView() {
        if (user.getSession().getAbstractDeadline().compareTo(new Date()) >= 0) {
            loadMainView("DateError");
            labelReviewerDateError.setText("The bid period hasn't started yet, please come back after " + user.getSession().getAbstractDeadline().toString());
        }

            loadMainView("BidPapers");

            tableColumnBidPapersAllPapers.setCellValueFactory(new PropertyValueFactory<Paper, String>("title"));
            List<Paper> papers = new ArrayList<>();
            List<Paper> papers2 = paperService.getAll();
            List<Paper> reviewerPapers = reviewer.getPapersToReview();

            if (reviewerPapers != null) {
                for (int i = 0; i < papers2.size(); i++) {
                    boolean found = false;
                    for (Paper p : reviewerPapers) {
                        if (p.getId().equals(papers2.get(i).getId())) {
                            found = true;
                            break;
                        }
                    }

                    if (found == false) {
                        papers.add(papers2.get(i));
                    }
                }
            }

            bidPapersAllPapersModel = FXCollections.observableList(papers);
            tableViewBidPapers.setItems(bidPapersAllPapersModel);

            tableColumnBidPapersAdded.setCellValueFactory(new PropertyValueFactory<Paper, String>("title"));
            if (reviewerPapers != null) {
                bidPapersAddedPapersModel = FXCollections.observableList(reviewerPapers);
            } else {
                bidPapersAddedPapersModel = FXCollections.observableArrayList(new ArrayList<Paper>());
            }
            tableViewBidPapersAdded.setItems(bidPapersAddedPapersModel);

    }

    @FXML
    public void handleBidPapersSelectionChanged() {
        Paper paper = tableViewBidPapers.getSelectionModel().getSelectedItem();

        if (paper != null) {
            textAreaBidPapersPaperAbstract.setText(paper.getPaperAbstract().getText());
            textAreaBidPapersPaperAbstract.setEditable(false);
        }
    }

    @FXML
    public void handleBidPapersBid() {
        Paper paper = tableViewBidPapers.getSelectionModel().getSelectedItem();

        if (paper != null) {

            List<Paper> reviewerPapers = new ArrayList<>(reviewer.getPapersToReview());
            reviewerPapers.add(paper);
            reviewer.setPapersToReview(reviewerPapers);
            reviewerService.update(reviewer);

            bidPapersAllPapersModel.remove(paper);
            bidPapersAddedPapersModel.add(paper);
        }
    }

    @FXML
    public void handleShowReviewPapersView() {
        loadMainView("ReviewPapers");
        toggleGroupAcceptanceType = new ToggleGroup();
        radioButtonReviewPaperStrongReject.setToggleGroup(toggleGroupAcceptanceType);
        radioButtonReviewPaperReject.setToggleGroup(toggleGroupAcceptanceType);
        radioButtonReviewPaperWeakReject.setToggleGroup(toggleGroupAcceptanceType);
        radioButtonReviewPaperBorderline.setToggleGroup(toggleGroupAcceptanceType);
        radioButtonReviewPaperWeakAccept.setToggleGroup(toggleGroupAcceptanceType);
        radioButtonReviewPaperAccept.setToggleGroup(toggleGroupAcceptanceType);
        radioButtonReviewPaperStrongAccept.setToggleGroup(toggleGroupAcceptanceType);
        tableColumnReviewPapersReviewed.setCellValueFactory(new PropertyValueFactory<Paper, String>("title"));
        tableColumnReviewPapers.setCellValueFactory(new PropertyValueFactory<Paper, String>("title"));

        List<Paper> papersToReview = reviewer.getPapersToReview();
        List<Paper> reviewedPapers = reviewer.getPapersReviewed();

        if (papersToReview != null) {
            reviewPapersAllPapersModel = FXCollections.observableList(papersToReview);
        }
        else {
            reviewPapersAllPapersModel = FXCollections.observableArrayList(new ArrayList<Paper>());
        }

        if (reviewedPapers != null) {
            reviewPapersReviewedPapersModel = FXCollections.observableList(reviewedPapers);
        }
        else {
            reviewPapersReviewedPapersModel = FXCollections.observableArrayList(new ArrayList<Paper>());
        }

        tableViewReviewPapers.setItems(reviewPapersAllPapersModel);
        tableViewReviewPapersReviewed.setItems(reviewPapersReviewedPapersModel);
    }

    @FXML
    public void handleReviewPapersReview() {
        Paper paper = tableViewReviewPapers.getSelectionModel().getSelectedItem();

        if (paper != null) {
            AcceptanceType acceptanceType = AcceptanceType.BORDERLINE_PAPER;

            RadioButton radioButton = (RadioButton) toggleGroupAcceptanceType.getSelectedToggle();

            if (radioButton.getId().equals("radioButtonReviewPaperStrongReject")) {
                acceptanceType = AcceptanceType.STRONG_REJECT;
            }
            if (radioButton.getId().equals("radioButtonReviewPaperReject")) {
                acceptanceType = AcceptanceType.REJECT;
            }
            if (radioButton.getId().equals("radioButtonReviewPaperWeakReject")) {
                acceptanceType = AcceptanceType.STRONG_REJECT;
            }
            if (radioButton.getId().equals("radioButtonReviewPaperBorderline")) {
                acceptanceType = AcceptanceType.BORDERLINE_PAPER;
            }
            if (radioButton.getId().equals("radioButtonReviewPaperWeakAccept")) {
                acceptanceType = AcceptanceType.WEAK_ACCEPT;
            }
            if (radioButton.getId().equals("radioButtonReviewPaperAccept")) {
                acceptanceType = AcceptanceType.ACCEPT;
            }
            if (radioButton.getId().equals("radioButtonReviewPaperStrongAccept")) {
                acceptanceType = AcceptanceType.STRONG_ACCEPT;
            }

            PaperReview paperReview = reviewerService.reviewPaper(reviewer, paper,textAreaReviewPapersComment.getText(), acceptanceType);

            reviewPapersAllPapersModel.remove(paper);
            reviewPapersReviewedPapersModel.add(paper);

            reviewer.setPapersToReview(reviewPapersAllPapersModel);
            reviewer.setPapersReviewed(reviewPapersReviewedPapersModel);

            reviewerService.update(reviewer);
            List<PaperReview> paperReviews = new ArrayList<>(paper.getReviews());
            paperReviews.add(paperReview);
            paper.setReviews(paperReviews);

            paperService.updatePaper(paper);
        }
    }

    @FXML
    public void handleSelectionChangedReviewdPapers() {
        Paper paper = tableViewReviewPapersReviewed.getSelectionModel().getSelectedItem();

        if (paper != null) {
            List<PaperReview> reviews = paperService.getReviewsForPaper(paper);
            List<String> reviewsModel = new ArrayList<>();

            for (PaperReview review : reviews) {
                String qualifier = "";

                switch (review.getQualifier()) {
                    case 0:
                        qualifier = "STRONG REJECT";
                        break;
                    case 1:
                        qualifier = "REJECT";
                        break;
                    case 2:
                        qualifier = "WEAK REJECT";
                        break;
                    case 3:
                        qualifier = "BORDERLINE PAPER";
                        break;
                    case 4:
                        qualifier = "WEAK ACCEPT";
                        break;
                    case 5:
                        qualifier = "ACCEPT";
                        break;
                    case 6:
                        qualifier = "STRONG ACCEPT";
                        break;
                }

                String s = review.getReviewer().getUser().getFirstName() + " " + review.getReviewer().getUser().getLastName() + ": " + qualifier;
                reviewsModel.add(s);
            }

            listViewReviewedPapersAllVotes.setItems(FXCollections.observableList(reviewsModel));
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
