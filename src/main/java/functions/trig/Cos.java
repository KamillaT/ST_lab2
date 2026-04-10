package functions.trig;

import functions.AbstractFunction;
import functions.Function;

public class Cos extends AbstractFunction {

    private final Function sin;

    public Cos(Function sin) {
        this.sin = sin;
    }

    @Override
    public double calculate(double x, double eps) {
        validateEps(eps);
        return sin.calculate(x + Math.PI / 2.0, eps);
    }
}