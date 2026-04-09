package support.stubs;

import support.TabulatedFunctionStub;

public class SinStub extends TabulatedFunctionStub {

    public SinStub(double[][] table) {
        super(table);
    }

    public static SinStub at(double x, double value) {
        return new SinStub(new double[][]{{x, value}});
    }
}
