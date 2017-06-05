package model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Reviewer")
public class Reviewer {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "papers_to_review")
    private List<Paper> papersToReview;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "papers_reviewed")
    private List<Paper> papersReviewed;

    @OneToOne
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Paper> getPapersReviewed() {
        return papersReviewed;
    }

    public void setPapersReviewed(List<Paper> papersReviewed) {
        this.papersReviewed = papersReviewed;
    }
}
