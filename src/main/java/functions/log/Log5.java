package functions.log;

import functions.AbstractFunction;
import functions.Function;

public class Log5 extends AbstractFunction {

    private final Function ln;

    public Log5(Function ln) {
        this.ln = ln;
    }

    @Override
    public double calculate(double x, double eps) {
        validateEps(eps);

        if (x <= 0) {
            return Double.NaN;
        }

        double lnX = ln.calculate(x, eps);
        double ln5 = ln.calculate(5.0, eps);

        if (Double.isNaN(lnX) || Double.isNaN(ln5) || ln5 == 0.0) {
            return Double.NaN;
        }

        return lnX / ln5;
    }
}