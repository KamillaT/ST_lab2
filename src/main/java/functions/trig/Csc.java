package functions.trig;

import functions.AbstractFunction;
import functions.Function;

public class Csc extends AbstractFunction {

    private final Function sin;

    public Csc(Function sin) {
        this.sin = sin;
    }

    @Override
    public double calculate(double x, double eps) {
        validateEps(eps);

        double sinX = sin.calculate(x, eps);

        if (Double.isNaN(sinX) || Math.abs(sinX) < eps) {
            return Double.NaN;
        }

        return 1.0 / sinX;
    }
}