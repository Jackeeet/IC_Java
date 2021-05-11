package dbo;

import javax.persistence.*;

@Entity
@Table(name = "t_message_receiver")
public class MessageToReceiver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private Message message;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    public MessageToReceiver() {

    }

    public MessageToReceiver(Message message, User receiver) {
        this.receiver = receiver;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}
