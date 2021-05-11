package dao;

import dbo.Message;
import dbo.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class UserDao {

    public void create(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    public User getUserById(int id) {
        return HibernateUtil
                .getSessionFactory()
                .openSession()
                .get(User.class, id);
    }

    public void update(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
    }

    public void delete(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        User userToDelete = session.load(User.class, user.getId());
        if (userToDelete != null) {
            session.delete(userToDelete);
        }
        transaction.commit();
        session.close();
    }

    public List<User> getAllUsers() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM User";
        Query query = session.createQuery(hql);
        List<User> users = query.list();

        transaction.commit();
        session.close();
        return users;
    }

    public void printAllUsersInfo(){
        List<User> users = getAllUsers();
        for (User user: users) {
            printFullUserInfo(user);
        }
    }

    private void printFullUserInfo(User user){
        System.out.println(
                "ID: " + user.getId() +
                " Name: " + user.getName() +
                " Surname: " + user.getSurname() +
                " Date of birth: " + user.getDateOfBirth() +
                " Total sent: " + user.getSentMessages().size() +
                " Total received: " + user.getReceivedMessages().size());
    }


    public User getShortestMessageSender() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "FROM Message";
        Query query = session.createQuery(hql);
        List<Message> messages = query.list();
        int id = getShortestMsgId(messages);
        User user = getUserById(id);

        transaction.commit();
        session.close();
        return user;
    }

    public List<User> getUsersWithTopic(String topic) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "select distinct receiver.id from MessageToReceiver " +
                "where message.topic = :msgTopic";

        Query query = session.createQuery(hql);
        query.setParameter("msgTopic", topic);
        List<Integer> ids = query.list();

        ArrayList<User> users = new ArrayList<>();
        for (int id : ids) {
            users.add(getUserById(id));
        }
        transaction.commit();
        session.close();
        return users;
    }

    public List<User> getUsersWithoutTopic(String topic){
        List<User> allUsers = getAllUsers();
        List<User> receivedUsers = getUsersWithTopic(topic);
        allUsers.removeAll(receivedUsers);
        return allUsers;
    }

    private int getShortestMsgId(List<Message> messages) {
        int minLength = Integer.MAX_VALUE;
        int id = -1;
        for (int i = 0; i < messages.size(); i++) {
            Message msg = messages.get(i);
            int msgLength = msg.getContent().length();
            if (msgLength < minLength) {
                id = msg.getId();
                minLength = msgLength;
            }
        }
        return id;
    }
}
