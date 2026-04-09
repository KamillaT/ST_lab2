package support;

public final class ExpectedSystemCalculator {

    private ExpectedSystemCalculator() {
    }

    public static double calculate(double x, double eps) {
        if (x <= 0) {
            return calculateTrigBranch(x, eps);
        }
        return calculateLogBranch(x, eps);
    }

    public static double calculateTrigBranch(double x, double eps) {
        double sinX = Math.sin(x);
        double cosX = Math.cos(x);

        if (Math.abs(sinX) < eps || Math.abs(cosX) < eps) {
            return Double.NaN;
        }

        double tanX = Math.tan(x);
        double secX = 1.0 / cosX;
        double cscX = 1.0 / sinX;
        double denominator = tanX - Math.pow(cosX, 3);

        if (Math.abs(tanX) < eps || Math.abs(denominator) < eps) {
            return Double.NaN;
        }

        double numerator = ((sinX / cscX) - secX) * tanX;
        return (numerator / denominator) / tanX;
    }

    public static double calculateLogBranch(double x, double eps) {
        if (x <= 0) {
            return Double.NaN;
        }

        double lnX = Math.log(x);
        double log5X = lnX / Math.log(5.0);
        double denominator = log5X / lnX;

        if (Math.abs(lnX) < eps || Math.abs(denominator) < eps) {
            return Double.NaN;
        }

        double log10X = lnX / Math.log(10.0);
        double numerator = Math.pow(Math.pow(log5X, 2) + log10X, 2);
        return Math.pow(numerator / denominator, 3);
    }
}
