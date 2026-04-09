package support.stubs;

import support.TabulatedFunctionStub;

public class TanStub extends TabulatedFunctionStub {

    public TanStub(double[][] table) {
        super(table);
    }

    public static TanStub at(double x, double value) {
        return new TanStub(new double[][]{{x, value}});
    }
}
