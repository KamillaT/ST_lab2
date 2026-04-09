package support.stubs;

import support.TabulatedFunctionStub;

public class SecStub extends TabulatedFunctionStub {

    public SecStub(double[][] table) {
        super(table);
    }

    public static SecStub at(double x, double value) {
        return new SecStub(new double[][]{{x, value}});
    }
}
