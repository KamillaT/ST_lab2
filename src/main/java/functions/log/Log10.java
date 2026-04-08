package functions.log;

import functions.AbstractFunction;
import functions.Function;

public class Log10 extends AbstractFunction {

    private final Function ln;

    public Log10(Function ln) {
        this.ln = ln;
    }

    @Override
    public double calculate(double x, double eps) {
        validateEps(eps);

        if (x <= 0) {
            return Double.NaN;
        }

        double lnX = ln.calculate(x, eps);
        double ln10 = ln.calculate(10.0, eps);

        if (Double.isNaN(lnX) || Double.isNaN(ln10) || ln10 == 0.0) {
            return Double.NaN;
        }

        return lnX / ln10;
    }
}