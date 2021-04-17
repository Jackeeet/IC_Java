import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class CalculatorTest {
    double a = 2.0;
    double b = 3.0;
    double delta = 1e-10;

    @Test
    public void add() {
        double result = Calculator.add(a, b);
        Assert.assertEquals(5.0, result, delta);
    }

    @Test
    public void subtract() {
        double result = Calculator.subtract(a, b);
        Assert.assertEquals(-1.0, result, delta);
    }

    @Test
    public void multiply() {
        double result = Calculator.multiply(a, b);
        Assert.assertEquals(6.0, result, delta);
    }

    @Test
    public void divide() throws Exception {
        double expected = a / b;
        double result = Calculator.divide(a, b);
        Assert.assertEquals(expected, result, delta);
    }

    @Test
    public void aggregate() {
        double[] data = new double[10];
        for (int i = 0; i < 10; i++) {
            data[i] = i + 1;
        }
        double result = Calculator.aggregate(data);
        Assert.assertEquals(55.0, result, delta);
    }
}