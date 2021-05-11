package dbClasses;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DbQueryUtils {

    public static void getMinMsgLengthUser() {
        String getUserSql =
                "select name, surname " +
                        "from t_user left join t_letter on t_user.id = t_letter.id " +
                        "where length(t_letter.content) = (select min(length(content)) from t_letter)";
        String[] colNames = {"name", "surname"};

        try (Connection connection = DbConnectionUtils.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(getUserSql);
            rs.next();
            System.out.println(parseResult(rs, colNames));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void getAllUserInfo() {
        String getUserSql =
                "select t2.sender_id as userid, t1.name, t1.surname, t1.dob, count(t2.id) as sent " +
                        "from t_user t1 left join t_letter t2 on t1.id = t2.sender_id " +
                        "group by t2.sender_id, t1.name, t1.surname, t1.dob " +
                        "order by t2.sender_id";
        String[] colNames = {"name", "surname", "dob", "sent"};

        try (Connection connection = DbConnectionUtils.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(getUserSql);
            while (rs.next()) {
                String id = rs.getString("userid");
                String received = getMsgReceivedByUser(connection, id);
                System.out.println(id + " " + parseResult(rs, colNames) + received);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void getUsersReceivedTopic(String topic) {
        String[] colNames = {"id", "name", "surname", "dob"};

        try (Connection connection = DbConnectionUtils.getConnection()) {
            ResultSet rs = getIdsReceivedTopic(connection, topic);
            ResultSet userRs;
            while (rs.next()) {
                String id = rs.getString("userid");
                userRs = getUserByID(connection, id);
                userRs.next();
                System.out.println(parseResult(userRs, colNames));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void getUsersNotReceivedTopic(String topic) {
        String[] colNames = {"id", "name", "surname", "dob"};

        try (Connection connection = DbConnectionUtils.getConnection()) {
            ArrayList<String> userIds = getAllUserIds();
            ResultSet rs = getIdsReceivedTopic(connection, topic);
            while (rs.next()) {
                userIds.remove(rs.getString("userid"));
            }
            for (String id : userIds) {
                rs = getUserByID(connection, id);
                rs.next();
                System.out.println(parseResult(rs, colNames));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<String> getAllTopics() {
        ArrayList<String> result = new ArrayList<>();
        String getTopics = "select distinct topic from t_letter";

        try (Connection c = DbConnectionUtils.getConnection();
             Statement s = c.createStatement()) {
            ResultSet rs = s.executeQuery(getTopics);
            while (rs.next()) {
                result.add(rs.getString("topic"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static ArrayList<String> getAllUserIds() {
        ArrayList<String> result = new ArrayList<>();
        String getUserIds = "select id from t_user";
        try (Connection c = DbConnectionUtils.getConnection();
             Statement s = c.createStatement()) {
            ResultSet rs = s.executeQuery(getUserIds);
            while (rs.next()) {
                result.add(rs.getString("id"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    private static ResultSet getUserByID(Connection c, String id) throws SQLException {
        String getUserSql = "select * from t_user where id = " + id;
        Statement s = c.createStatement();
        return s.executeQuery(getUserSql);
    }

    private static ResultSet getIdsReceivedTopic(Connection c, String topic) throws SQLException {
        String getReceiverIdsSql =
                "select distinct unnest(receiver_ids) as userid " +
                        "from t_letter " +
                        "where topic = '" + topic + "'";
        Statement s = c.createStatement();
        return s.executeQuery(getReceiverIdsSql);
    }

    private static String getMsgReceivedByUser(Connection c, String userid) throws SQLException {
        String getMsgCountSql =
                "select count(t1.id) " +
                        "from t_letter t1 left join t_user t2 on t1.sender_id = t2.id " +
                        "where " + userid + " = any(receiver_ids) ";
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery(getMsgCountSql);
        rs.next();
        return rs.getString("count");
    }

    private static String parseResult(ResultSet rs, String[] colNames) throws SQLException {
        StringBuilder sb = new StringBuilder();
        for (String col : colNames) {
            sb.append(rs.getString(col));
            sb.append(' ');
        }
        return sb.toString();
    }
}
