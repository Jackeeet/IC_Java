package dbClasses;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DbDataModifier {
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void sendMsg(String senderId, String topic, String content, String[] receiverIds) {
        String sendMsgSql =
            "insert into t_letter(sender_id, receiver_ids, topic, content, date_sent)\n" +
            getInsertionValues(senderId, topic, content, receiverIds);
        try (Connection connection = DbConnectionUtils.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sendMsgSql);
            System.out.println("Сообщение успешно отправлено.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String parseReceiverIds(String[] receiverIds) {
        StringBuilder sb = new StringBuilder(", array[");
        for (String id : receiverIds) {
            sb.append(id).append(", ");
        }
        int len = sb.length();
        sb.replace(len - 2, len, "], ");
        return sb.toString();
    }

    private static String getInsertionValues(String senderId, String topic, String content, String[] receiverIds) {
        return "values (" + senderId + parseReceiverIds(receiverIds) +
                " '" + topic + "'," +
                " '" + content + "'," +
                " '" + dtf.format(LocalDateTime.now()) + "')";
    }
}
