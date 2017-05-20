package model;

import javax.persistence.*;

@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue
    Integer id;

    String firstName;

    String lastName;

    String email;

    String affiliation;

    String password;

    Boolean registrationFee;

    Integer userRole;

    @ManyToOne
    @JoinTable(name = "sessions_users")
    ConferenceSession session;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getRegistrationFee() {
        return registrationFee;
    }

    public void setRegistrationFee(Boolean registrationFee) {
        this.registrationFee = registrationFee;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public ConferenceSession getSession() {
        return session;
    }

    public void setSession(ConferenceSession session) {
        this.session = session;
    }
}
