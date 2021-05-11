package dbo;

import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Temporal(TemporalType.DATE)
    @Column(name = "dob")
    private Date dateOfBirth;

    @OneToMany(mappedBy = "sender", fetch = FetchType.EAGER)
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "receiver", fetch = FetchType.EAGER)
    private List<MessageToReceiver> receivedMessages;

    public User() {
    }

    public User(String name, String surname, Date dob) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dob;
        sentMessages = new ArrayList<>();
        receivedMessages = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<Message> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(List<Message> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public List<MessageToReceiver> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(List<MessageToReceiver> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
