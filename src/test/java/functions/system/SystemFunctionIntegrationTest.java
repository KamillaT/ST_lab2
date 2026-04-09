package functions.system;

import functions.Function;
import functions.log.Ln;
import functions.log.Log10;
import functions.log.Log5;
import functions.trig.Cos;
import functions.trig.Csc;
import functions.trig.Sec;
import functions.trig.Sin;
import functions.trig.Tan;
import org.junit.jupiter.api.Test;
import support.ExpectedSystemCalculator;
import support.stubs.CosStub;
import support.stubs.CscStub;
import support.stubs.LnStub;
import support.stubs.Log10Stub;
import support.stubs.Log5Stub;
import support.stubs.SecStub;
import support.stubs.SinStub;
import support.stubs.TanStub;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SystemFunctionIntegrationTest {

    private static final double EPS = 1e-6;
    private static final double ASSERT_EPS = 1e-4;
    private static final double TRIG_X = -Math.PI / 4;
    private static final double LOG_X = 2.0;

    @Test
    void computesTrigBranchWithAllModulesStubbed() {
        SystemFunction system = new SystemFunction(
                SinStub.at(TRIG_X, Math.sin(TRIG_X)),
                CosStub.at(TRIG_X, Math.cos(TRIG_X)),
                TanStub.at(TRIG_X, Math.tan(TRIG_X)),
                SecStub.at(TRIG_X, 1.0 / Math.cos(TRIG_X)),
                CscStub.at(TRIG_X, 1.0 / Math.sin(TRIG_X)),
                LnStub.at(LOG_X, Math.log(LOG_X)),
                Log5Stub.at(LOG_X, Math.log(LOG_X) / Math.log(5.0)),
                Log10Stub.at(LOG_X, Math.log(LOG_X) / Math.log(10.0))
        );

        assertEquals(ExpectedSystemCalculator.calculate(TRIG_X, EPS), system.calculate(TRIG_X, EPS), ASSERT_EPS);
    }

    @Test
    void integratesRealSinIntoTrigBranch() {
        SystemFunction system = new SystemFunction(
                new Sin(),
                CosStub.at(TRIG_X, Math.cos(TRIG_X)),
                TanStub.at(TRIG_X, Math.tan(TRIG_X)),
                SecStub.at(TRIG_X, 1.0 / Math.cos(TRIG_X)),
                CscStub.at(TRIG_X, 1.0 / Math.sin(TRIG_X)),
                LnStub.at(LOG_X, Math.log(LOG_X)),
                Log5Stub.at(LOG_X, Math.log(LOG_X) / Math.log(5.0)),
                Log10Stub.at(LOG_X, Math.log(LOG_X) / Math.log(10.0))
        );

        assertEquals(ExpectedSystemCalculator.calculate(TRIG_X, EPS), system.calculate(TRIG_X, EPS), ASSERT_EPS);
    }

    @Test
    void integratesRealCosIntoTrigBranch() {
        SystemFunction system = new SystemFunction(
                SinStub.at(TRIG_X, Math.sin(TRIG_X)),
                new Cos(),
                TanStub.at(TRIG_X, Math.tan(TRIG_X)),
                SecStub.at(TRIG_X, 1.0 / Math.cos(TRIG_X)),
                CscStub.at(TRIG_X, 1.0 / Math.sin(TRIG_X)),
                LnStub.at(LOG_X, Math.log(LOG_X)),
                Log5Stub.at(LOG_X, Math.log(LOG_X) / Math.log(5.0)),
                Log10Stub.at(LOG_X, Math.log(LOG_X) / Math.log(10.0))
        );

        assertEquals(ExpectedSystemCalculator.calculate(TRIG_X, EPS), system.calculate(TRIG_X, EPS), ASSERT_EPS);
    }

    @Test
    void integratesRealTanIntoTrigBranch() {
        Function sin = new Sin();
        Function cos = new Cos();
        SystemFunction system = new SystemFunction(
                sin,
                cos,
                new Tan(sin, cos),
                SecStub.at(TRIG_X, 1.0 / Math.cos(TRIG_X)),
                CscStub.at(TRIG_X, 1.0 / Math.sin(TRIG_X)),
                LnStub.at(LOG_X, Math.log(LOG_X)),
                Log5Stub.at(LOG_X, Math.log(LOG_X) / Math.log(5.0)),
                Log10Stub.at(LOG_X, Math.log(LOG_X) / Math.log(10.0))
        );

        assertEquals(ExpectedSystemCalculator.calculate(TRIG_X, EPS), system.calculate(TRIG_X, EPS), ASSERT_EPS);
    }

    @Test
    void integratesRealSecIntoTrigBranch() {
        Function sin = new Sin();
        Function cos = new Cos();
        SystemFunction system = new SystemFunction(
                sin,
                cos,
                TanStub.at(TRIG_X, Math.tan(TRIG_X)),
                new Sec(cos),
                CscStub.at(TRIG_X, 1.0 / Math.sin(TRIG_X)),
                LnStub.at(LOG_X, Math.log(LOG_X)),
                Log5Stub.at(LOG_X, Math.log(LOG_X) / Math.log(5.0)),
                Log10Stub.at(LOG_X, Math.log(LOG_X) / Math.log(10.0))
        );

        assertEquals(ExpectedSystemCalculator.calculate(TRIG_X, EPS), system.calculate(TRIG_X, EPS), ASSERT_EPS);
    }

    @Test
    void integratesRealCscIntoTrigBranch() {
        Function sin = new Sin();
        SystemFunction system = new SystemFunction(
                sin,
                new Cos(),
                TanStub.at(TRIG_X, Math.tan(TRIG_X)),
                SecStub.at(TRIG_X, 1.0 / Math.cos(TRIG_X)),
                new Csc(sin),
                LnStub.at(LOG_X, Math.log(LOG_X)),
                Log5Stub.at(LOG_X, Math.log(LOG_X) / Math.log(5.0)),
                Log10Stub.at(LOG_X, Math.log(LOG_X) / Math.log(10.0))
        );

        assertEquals(ExpectedSystemCalculator.calculate(TRIG_X, EPS), system.calculate(TRIG_X, EPS), ASSERT_EPS);
    }

    @Test
    void returnsNaNAtTrigBranchDiscontinuity() {
        Function system = new SystemFunction(
                new Sin(),
                new Cos(),
                new Tan(new Sin(), new Cos()),
                new Sec(new Cos()),
                new Csc(new Sin()),
                new Ln(),
                new Log5(new Ln()),
                new Log10(new Ln())
        );

        assertTrue(Double.isNaN(system.calculate(0.0, EPS)));
        assertTrue(Double.isNaN(system.calculate(-Math.PI / 2, EPS)));
    }

    @Test
    void computesLogBranchWithAllModulesStubbed() {
        SystemFunction system = new SystemFunction(
                SinStub.at(TRIG_X, Math.sin(TRIG_X)),
                CosStub.at(TRIG_X, Math.cos(TRIG_X)),
                TanStub.at(TRIG_X, Math.tan(TRIG_X)),
                SecStub.at(TRIG_X, 1.0 / Math.cos(TRIG_X)),
                CscStub.at(TRIG_X, 1.0 / Math.sin(TRIG_X)),
                LnStub.at(LOG_X, Math.log(LOG_X)),
                Log5Stub.at(LOG_X, Math.log(LOG_X) / Math.log(5.0)),
                Log10Stub.at(LOG_X, Math.log(LOG_X) / Math.log(10.0))
        );

        assertEquals(ExpectedSystemCalculator.calculate(LOG_X, EPS), system.calculate(LOG_X, EPS), ASSERT_EPS);
    }

    @Test
    void integratesRealLnIntoLogBranch() {
        SystemFunction system = new SystemFunction(
                SinStub.at(TRIG_X, Math.sin(TRIG_X)),
                CosStub.at(TRIG_X, Math.cos(TRIG_X)),
                TanStub.at(TRIG_X, Math.tan(TRIG_X)),
                SecStub.at(TRIG_X, 1.0 / Math.cos(TRIG_X)),
                CscStub.at(TRIG_X, 1.0 / Math.sin(TRIG_X)),
                new Ln(),
                Log5Stub.at(LOG_X, Math.log(LOG_X) / Math.log(5.0)),
                Log10Stub.at(LOG_X, Math.log(LOG_X) / Math.log(10.0))
        );

        assertEquals(ExpectedSystemCalculator.calculate(LOG_X, EPS), system.calculate(LOG_X, EPS), ASSERT_EPS);
    }

    @Test
    void integratesRealLog5IntoLogBranch() {
        Function ln = new Ln();
        SystemFunction system = new SystemFunction(
                SinStub.at(TRIG_X, Math.sin(TRIG_X)),
                CosStub.at(TRIG_X, Math.cos(TRIG_X)),
                TanStub.at(TRIG_X, Math.tan(TRIG_X)),
                SecStub.at(TRIG_X, 1.0 / Math.cos(TRIG_X)),
                CscStub.at(TRIG_X, 1.0 / Math.sin(TRIG_X)),
                ln,
                new Log5(ln),
                Log10Stub.at(LOG_X, Math.log(LOG_X) / Math.log(10.0))
        );

        assertEquals(ExpectedSystemCalculator.calculate(LOG_X, EPS), system.calculate(LOG_X, EPS), ASSERT_EPS);
    }

    @Test
    void integratesRealLog10IntoLogBranch() {
        Function ln = new Ln();
        SystemFunction system = new SystemFunction(
                SinStub.at(TRIG_X, Math.sin(TRIG_X)),
                CosStub.at(TRIG_X, Math.cos(TRIG_X)),
                TanStub.at(TRIG_X, Math.tan(TRIG_X)),
                SecStub.at(TRIG_X, 1.0 / Math.cos(TRIG_X)),
                CscStub.at(TRIG_X, 1.0 / Math.sin(TRIG_X)),
                ln,
                new Log5(ln),
                new Log10(ln)
        );

        assertEquals(ExpectedSystemCalculator.calculate(LOG_X, EPS), system.calculate(LOG_X, EPS), ASSERT_EPS);
    }

    @Test
    void returnsNaNAtLogBranchDiscontinuity() {
        Function ln = new Ln();
        Function system = new SystemFunction(
                new Sin(),
                new Cos(),
                new Tan(new Sin(), new Cos()),
                new Sec(new Cos()),
                new Csc(new Sin()),
                ln,
                new Log5(ln),
                new Log10(ln)
        );

        assertTrue(Double.isNaN(system.calculate(1.0, EPS)));
    }
}
