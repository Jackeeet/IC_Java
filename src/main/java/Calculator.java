import java.util.List;

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

}
