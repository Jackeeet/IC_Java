package dao;

import dbo.Message;
import dbo.MessageToReceiver;
import dbo.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtil;

import java.util.List;

public class MessageDao {

    public void create(Message message) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(message);
        transaction.commit();
        session.close();
    }

    public Message getMessageById(int id) {
        return HibernateUtil
                .getSessionFactory()
                .openSession()
                .get(Message.class, id);
    }

    public void update(Message message) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(message);
        transaction.commit();
        session.close();
    }

    public void delete(Message message) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Message messageToDelete = session.load(Message.class, message.getId());
        if (messageToDelete != null) {
            session.delete(message);
        }
        transaction.commit();
        session.close();
    }

    public List<Message> getAllMessages() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "FROM Message";
        Query query = session.createQuery(hql);
        List<Message> messages = query.list();
        transaction.commit();
        session.close();
        return messages;
    }

    public void sendToAllReceivers(Message message, List<User> receivers) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(message);
        transaction.commit();
        session.close();

        for (User user : receivers) {
            MessageToReceiver mtr = new MessageToReceiver(message, user);
            sendToReceiver(mtr);
            message.addReceiver(mtr);
        }
    }

    private void sendToReceiver(MessageToReceiver mtr){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.save(mtr);

        transaction.commit();
        session.close();
    }
}
