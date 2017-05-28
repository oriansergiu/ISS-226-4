package model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Author")
public class Author {
    @Id
    @GeneratedValue
    private Integer id;

    @OneToMany(cascade = { CascadeType.ALL },fetch = FetchType.EAGER)
    @JoinTable(name = "proposed_papers")
    private List<Paper> proposedPapers;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "accepted_papers")
    private List<Paper> acceptedPapers;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToOne
    private User user;

    public Author(User user){
        this.user = user;
    }

    public Author(){
        this.user = null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Paper> getProposedPapers() {
        return proposedPapers;
    }

    public void setProposedPapers(List<Paper> proposedPapers) {
        this.proposedPapers = proposedPapers;
    }

    public List<Paper> getAcceptedPapers() {
        return acceptedPapers;
    }

    public void setAcceptedPapers(List<Paper> acceptedPapers) {
        this.acceptedPapers = acceptedPapers;
    }
}
