import dbClasses.DbDataModifier;
import dbClasses.DbQueryUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static void showHelp() {
        System.out.println("min_msg_len        имя пользователя с самым коротким текстом сообщения");
        System.out.println("users              информация о всех пользователях");
        System.out.println("topics             информация о всех темах");
        System.out.println("rec <topic>        информация о пользователях, получивших хотя бы одно письмо с темой <topic>");
        System.out.println("not_rec <topic>    информация о пользователях, не получивших ни одного письма с темой <topic>");
        System.out.println("send               отправка сообщения");
        System.out.println("quit               завершает работу программы");
    }

    private static void parseCommand(String cmd) throws SQLException {
        String[] s = cmd.split(" ");
        switch (s[0]) {
            case "help":
                showHelp();
                break;
            case "min_msg_len":
                DbQueryUtils.getMinMsgLengthUser();
                break;
            case "topics":
                ArrayList<String> res = DbQueryUtils.getAllTopics();
                for (String topic : res) {
                    System.out.println(topic);
                }
                break;
            case "users":
                DbQueryUtils.getAllUserInfo();
                break;
            case "rec":
                DbQueryUtils.getUsersReceivedTopic(s[1]);
                break;
            case "not_rec":
                DbQueryUtils.getUsersNotReceivedTopic(s[1]);
                break;
            case "send":
                getSendParams();
                break;
            default:
                System.out.println("Неизвестная команда.");
                break;
        }
    }

    private static void getSendParams() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Укажите id отправителя:");
        String senderId = scanner.nextLine();
        System.out.println("Укажите тему письма:");
        String topic = scanner.nextLine();
        System.out.println("Укажите содержимое письма:");
        String content = scanner.nextLine();
        System.out.println("Укажите id получателей:");
        String[] ids = scanner.nextLine().split(" ");
        DbDataModifier.sendMsg(senderId, topic, content, ids);
    }

    private static void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите команду (для просмотра списка комманд введите help):");
        String command = scanner.nextLine();
        while (!command.equals("quit")) {
            try {
                parseCommand(command);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            command = scanner.nextLine();
        }
    }

    public static void main(String[] args){
        run();
    }
}
