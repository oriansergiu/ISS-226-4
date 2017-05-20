package model;

import javax.persistence.*;

@Entity
@Table(name = "Abstract")
public class Abstract {
    @Id
    @GeneratedValue
    private Integer id;

    private String text;

    @OneToOne
    private Paper paper;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
