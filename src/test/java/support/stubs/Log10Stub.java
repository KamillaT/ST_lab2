package support.stubs;

import support.TabulatedFunctionStub;

public class Log10Stub extends TabulatedFunctionStub {

    public Log10Stub(double[][] table) {
        super(table);
    }

    public static Log10Stub at(double x, double value) {
        return new Log10Stub(new double[][]{{x, value}});
    }
}
