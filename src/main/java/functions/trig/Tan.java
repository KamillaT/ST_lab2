package functions.trig;

import functions.AbstractFunction;
import functions.Function;

public class Tan extends AbstractFunction {

    private final Function sin;
    private final Function cos;

    public Tan(Function sin, Function cos) {
        this.sin = sin;
        this.cos = cos;
    }

    @Override
    public double calculate(double x, double eps) {
        validateEps(eps);

        double sinX = sin.calculate(x, eps);
        double cosX = cos.calculate(x, eps);

        if (Double.isNaN(sinX) || Double.isNaN(cosX) || Math.abs(cosX) < eps) {
            return Double.NaN;
        }

        return sinX / cosX;
    }
}