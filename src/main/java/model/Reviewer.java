package model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Reviewer")
public class Reviewer {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToMany
    @JoinTable(name = "papers_to_review")
    private List<Paper> papersToReview;

    @ManyToMany
    @JoinTable(name = "abstracts_to_review")
    private List<Abstract> abstractsToReview;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Paper> getPapersToReview() {
        return papersToReview;
    }

    public void setPapersToReview(List<Paper> papersToReview) {
        this.papersToReview = papersToReview;
    }

    public List<Abstract> getAbstractsToReview() {
        return abstractsToReview;
    }

    public void setAbstractsToReview(List<Abstract> abstractsToReview) {
        this.abstractsToReview = abstractsToReview;
    }
}
