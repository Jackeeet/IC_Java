package dbo;

import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @OneToMany(mappedBy = "message")
    private List<MessageToReceiver> receivers;

    @Column(name = "topic")
    private String topic;

    @Column(name = "content")
    private String content;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_sent")
    private Date dateSent;

    public Message() {
    }

    public Message(User sender, String topic, String content) {
        this.sender = sender;
        this.topic = topic;
        this.content = content;
        this.dateSent = new Date();
        this.receivers = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateSent() {
        return dateSent;
    }

    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public List<MessageToReceiver> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<MessageToReceiver> receivers) {
        this.receivers = receivers;
    }

    public void addReceiver(MessageToReceiver receiver) {
        this.receivers.add(receiver);
    }
}
