package functions.log;

import functions.AbstractFunction;

public class Ln extends AbstractFunction {

    @Override
    public double calculate(double x, double eps) {
        validateEps(eps);

        if (x <= 0) {
            return Double.NaN;
        }

        if (x == 1.0) {
            return 0.0;
        }

        double t = (x - 1) / (x + 1);
        double term = t;
        double sum = 0.0;
        int n = 0;

        while (Math.abs(term) > eps) {
            sum += term / (2 * n + 1);
            n++;
            term *= t * t;
        }

        return 2 * sum;
    }
}