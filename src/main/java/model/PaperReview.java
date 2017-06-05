package model;

import javax.persistence.*;

@Entity
@Table(name = "PaperReview")
public class PaperReview {
    @Id
    @GeneratedValue
    Integer id;

    @ManyToOne
    Reviewer reviewer;

    @ManyToOne
    Paper paper;

    Integer qualifier;

    @Column(length = 1000)
    String comment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Reviewer getReviewer() {
        return reviewer;
    }

    public void setReviewer(Reviewer reviewer) {
        this.reviewer = reviewer;
    }

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    public Integer getQualifier() {
        return qualifier;
    }

    public void setQualifier(Integer qualifier) {
        this.qualifier = qualifier;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
