package functions.system;

import functions.AbstractFunction;
import functions.Function;

public class SystemFunction extends AbstractFunction {

    private final Function sin;
    private final Function cos;
    private final Function tan;
    private final Function sec;
    private final Function csc;

    private final Function ln;
    private final Function log5;
    private final Function log10;

    public SystemFunction(Function sin,
                          Function cos,
                          Function tan,
                          Function sec,
                          Function csc,
                          Function ln,
                          Function log5,
                          Function log10) {
        this.sin = sin;
        this.cos = cos;
        this.tan = tan;
        this.sec = sec;
        this.csc = csc;
        this.ln = ln;
        this.log5 = log5;
        this.log10 = log10;
    }

    @Override
    public double calculate(double x, double eps) {
        validateEps(eps);

        if (x <= 0) {
            return calculateTrigBranch(x, eps);
        } else {
            return calculateLogBranch(x, eps);
        }
    }

    private double calculateTrigBranch(double x, double eps) {
        double sinX = sin.calculate(x, eps);
        double cosX = cos.calculate(x, eps);
        double tanX = tan.calculate(x, eps);
        double secX = sec.calculate(x, eps);
        double cscX = csc.calculate(x, eps);

        if (Double.isNaN(sinX) || Double.isNaN(cosX) || Double.isNaN(tanX)
                || Double.isNaN(secX) || Double.isNaN(cscX)) {
            return Double.NaN;
        }

        double denominator1 = tanX - Math.pow(cosX, 3);
        if (Math.abs(denominator1) < eps || Math.abs(tanX) < eps) {
            return Double.NaN;
        }

        double numeratorPart = ((sinX / cscX) - secX) * tanX;
        return (numeratorPart / denominator1) / tanX;
    }

    private double calculateLogBranch(double x, double eps) {
        double lnX = ln.calculate(x, eps);
        double log5X = log5.calculate(x, eps);
        double log10X = log10.calculate(x, eps);

        if (Double.isNaN(lnX) || Double.isNaN(log5X) || Double.isNaN(log10X)) {
            return Double.NaN;
        }

        double denominator = log5X / lnX;
        if (Math.abs(lnX) < eps || Math.abs(denominator) < eps) {
            return Double.NaN;
        }

        double numerator = Math.pow(Math.pow(log5X, 2) + log10X, 2);
        return Math.pow(numerator / denominator, 3);
    }
}