package functions.trig;

import functions.AbstractFunction;
import functions.Function;

public class Sec extends AbstractFunction {

    private final Function cos;

    public Sec(Function cos) {
        this.cos = cos;
    }

    @Override
    public double calculate(double x, double eps) {
        validateEps(eps);

        double cosX = cos.calculate(x, eps);

        if (Double.isNaN(cosX) || Math.abs(cosX) < eps) {
            return Double.NaN;
        }

        return 1.0 / cosX;
    }
}