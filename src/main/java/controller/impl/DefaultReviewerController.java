package controller.impl;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Abstract;
import model.Paper;
import model.Reviewer;
import model.User;
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

    public TableColumn<Paper, String> tableColumnBidPapersAdded;

    private ObservableList<Paper> bidPapersAllPapersModel;
    private ObservableList<Paper> bidPapersAddedPapersModel;

    @FXML
    public void initialize() {

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

        Reviewer reviewer = reviewerService.getReviewerByUser(user);

        if (reviewer == null) {
            reviewer = new Reviewer();
            reviewer.setUser(user);
            reviewerService.save(reviewer);
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
    }

    @FXML
    public void handleShowBinPapersView() {
        if (user.getSession().getAbstractDeadline().compareTo(new Date()) >= 0) {
            loadMainView("DateError");
            labelReviewerDateError.setText("The bid period hasn't started yet, please come back after " + user.getSession().getAbstractDeadline().toString());
        }
        else {
            loadMainView("BidPapers");
            tableColumnBidPapersAllPapers.setCellValueFactory(new PropertyValueFactory<Paper, String>("title"));
            List<Paper> papers = paperService.getAll();
            List<Paper> reviewerPapers = reviewer.getPapersToReview();

            if (reviewerPapers != null) {
                for (int i = 0; i < papers.size(); i++) {
                    for (Paper p : reviewerPapers) {
                        if (p.getId().equals(papers.get(i).getId())) ;
                        papers.remove(i);
                        break;
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
            reviewerService.update(reviewer);

            bidPapersAllPapersModel.remove(paper);
            bidPapersAddedPapersModel.add(paper);
        }
    }
}
