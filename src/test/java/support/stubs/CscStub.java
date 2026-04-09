package support.stubs;

import support.TabulatedFunctionStub;

public class CscStub extends TabulatedFunctionStub {

    public CscStub(double[][] table) {
        super(table);
    }

    public static CscStub at(double x, double value) {
        return new CscStub(new double[][]{{x, value}});
    }
}
