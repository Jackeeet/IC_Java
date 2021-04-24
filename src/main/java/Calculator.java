import java.util.Scanner;

public class Calculator {

    public static double add(double a, double b) {
        return a + b;
    }

    public static double subtract(double a, double b) {
        return a - b;
    }

    public static double multiply(double a, double b) {
        return a * b;
    }

    public static double divide(double a, double b) throws Exception {
        if (b == 0) {
            throw new Exception("division by 0");
        }
        return a / b;
    }

    public static double aggregate(double[] arr) {
        double sum = 0.0;
        for (double num : arr) {
            sum += num;
        }
        return sum;
    }

    private static double parseCommand(String s, double a, double b) throws Exception {
        switch (s) {
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

    private static void runCalculator() throws Exception {
        Scanner scanner = new Scanner(System.in);
        String nums = scanner.nextLine();
        String[] n = nums.split(" ");
        double a = Double.parseDouble(n[0]);
        double b = Double.parseDouble(n[1]);
        String command = scanner.nextLine();
        double result = Calculator.parseCommand(command, a, b);
        System.out.println(result);
    }
}
