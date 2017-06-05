package controller.impl;

import controller.Controller;
import enums.AcceptanceType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.*;
import org.hibernate.Hibernate;
import service.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    ToggleGroup toggleGroupAcceptanceType;

    private ObservableList<Paper> bidPapersAllPapersModel;
    private ObservableList<Paper> bidPapersAddedPapersModel;

    private ObservableList<Paper> reviewPapersAllPapersModel;
    private ObservableList<Paper> reviewPapersReviewedPapersModel;

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

    @FXML
    public void handleShowBinPapersView() {
        if (user.getSession().getAbstractDeadline().compareTo(new Date()) >= 0) {
            loadMainView("DateError");
            labelReviewerDateError.setText("The bid period hasn't started yet, please come back after " + user.getSession().getAbstractDeadline().toString());
        }

            loadMainView("BidPapers");

            tableColumnBidPapersAllPapers.setCellValueFactory(new PropertyValueFactory<Paper, String>("title"));
            List<Paper> papers = paperService.getAll();
            List<Paper> reviewerPapers = reviewer.getPapersToReview();

            if (reviewerPapers != null) {
                for (int i = 0; i < papers.size(); i++) {
                    for (Paper p : reviewerPapers) {
                        if (p.getId().equals(papers.get(i).getId())) {
                            papers.remove(i);
                            break;
                        }
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

                String s = review.getReviewer().getUser().getFirstName() + " " + review.getReviewer().getUser().getFirstName() + ": " + qualifier;
                reviewsModel.add(s);
            }

            listViewReviewedPapersAllVotes.setItems(FXCollections.observableList(reviewsModel));
        }
    }
}
