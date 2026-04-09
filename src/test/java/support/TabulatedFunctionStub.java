package support;

import functions.Function;

public class TabulatedFunctionStub implements Function {

    private static final double LOOKUP_EPS = 1e-9;

    private final double[][] table;

    public TabulatedFunctionStub(double[][] table) {
        this.table = table;
    }

    @Override
    public double calculate(double x, double eps) {
        for (double[] row : table) {
            if (Math.abs(row[0] - x) < LOOKUP_EPS) {
                return row[1];
            }
        }
        throw new IllegalArgumentException("No stubbed value for x = " + x);
    }
}
