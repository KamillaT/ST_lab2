package support.stubs;

import support.TabulatedFunctionStub;

public class CosStub extends TabulatedFunctionStub {

    public CosStub(double[][] table) {
        super(table);
    }

    public static CosStub at(double x, double value) {
        return new CosStub(new double[][]{{x, value}});
    }
}
