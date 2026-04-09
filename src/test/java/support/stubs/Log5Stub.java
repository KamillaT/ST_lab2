package support.stubs;

import support.TabulatedFunctionStub;

public class Log5Stub extends TabulatedFunctionStub {

    public Log5Stub(double[][] table) {
        super(table);
    }

    public static Log5Stub at(double x, double value) {
        return new Log5Stub(new double[][]{{x, value}});
    }
}
