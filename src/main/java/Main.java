import java.util.Scanner;

public class Main {

    public static double parseCommand(String s, double a, double b) throws Exception {
        switch (s){
            case "add":
                return Calculator.add(a, b);
            case "subtract":
                return Calculator.subtract(a, b);
            case "multiply":
                return Calculator.multiply(a, b);
            case "divide":
                return Calculator.divide(a, b);
            default:
                throw new Exception("aaaa");
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String nums = scanner.nextLine();
        String[] n = nums.split(" ");
        double a = Double.parseDouble(n[0]);
        double b = Double.parseDouble(n[1]);
        String command = scanner.nextLine();
        double result = parseCommand(command, a, b);
        System.out.println(result);
    }
}
