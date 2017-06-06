package model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Section")
public class Section {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String description;

    @OneToOne(fetch = FetchType.EAGER)
    private User sectionChair;

    @ManyToMany
    @JoinTable(name = "sections_participants")
    private List<User> participants;


    @ManyToMany
    @JoinTable(name = "section_paper")
    private List<Paper> papers;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public List<Paper> getPapers() {
        return papers;
    }

    public void setPapers(List<Paper> papers) {
        this.papers = papers;
    }

    public User getUser(){return sectionChair;}

    public void setUser(User sectionChair){this.sectionChair=sectionChair;}
}
