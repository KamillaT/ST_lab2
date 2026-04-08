package functions.trig;

import functions.AbstractFunction;

public class Cos extends AbstractFunction {

    private static final double TWO_PI = 2 * Math.PI;

    @Override
    public double calculate(double x, double eps) {
        validateEps(eps);

        x = normalize(x);

        double term = 1.0;
        double sum = 1.0;
        int n = 1;

        while (Math.abs(term) > eps) {
            term *= -1 * x * x / ((2.0 * n - 1) * (2.0 * n));
            sum += term;
            n++;
        }

        return sum;
    }

    private double normalize(double x) {
        x %= TWO_PI;
        if (x > Math.PI) {
            x -= TWO_PI;
        } else if (x < -Math.PI) {
            x += TWO_PI;
        }
        return x;
    }
}