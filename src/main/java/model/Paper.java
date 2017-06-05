package model;

import javax.persistence.*;
import java.sql.Blob;
import java.util.List;

@Entity
@Table(name = "Paper")
public class Paper {
    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    private String coAuthors;

    private String keywords;

    private String topic;
    @Lob
    private Byte[] attachment;

    @OneToOne
    private Abstract paperAbstract;

    @ManyToOne
    private Author author;

    @OneToMany(fetch = FetchType.LAZY)
    private List<PaperReview> reviews;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoAuthors() {
        return coAuthors;
    }

    public void setCoAuthors(String coAuthors) {
        this.coAuthors = coAuthors;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(Byte[] attachment) {
        this.attachment = attachment;
    }

    public Abstract getPaperAbstract() {
        return paperAbstract;
    }

    public void setPaperAbstract(Abstract paperAbstract) {
        this.paperAbstract = paperAbstract;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }


}
