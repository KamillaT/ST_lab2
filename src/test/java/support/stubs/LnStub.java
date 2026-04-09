package support.stubs;

import support.TabulatedFunctionStub;

public class LnStub extends TabulatedFunctionStub {

    public LnStub(double[][] table) {
        super(table);
    }

    public static LnStub at(double x, double value) {
        return new LnStub(new double[][]{{x, value}});
    }
}
