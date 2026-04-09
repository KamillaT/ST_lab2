package functions;

import functions.log.Ln;
import functions.log.Log10;
import functions.log.Log5;
import functions.system.SystemFunction;
import functions.trig.Cos;
import functions.trig.Csc;
import functions.trig.Sec;
import functions.trig.Sin;
import functions.trig.Tan;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FunctionModulesTest {

    private static final double EPS = 1e-6;
    private static final double ASSERT_EPS = 1e-4;

    @Test
    void sinNormalizesPositiveAnglesGreaterThanPi() {
        assertEquals(Math.sin(4.0), new Sin().calculate(4.0, EPS), ASSERT_EPS);
    }

    @Test
    void sinNormalizesNegativeAnglesLessThanMinusPi() {
        assertEquals(Math.sin(-4.0), new Sin().calculate(-4.0, EPS), ASSERT_EPS);
    }

    @Test
    void cosNormalizesPositiveAnglesGreaterThanPi() {
        assertEquals(Math.cos(4.0), new Cos().calculate(4.0, EPS), ASSERT_EPS);
    }

    @Test
    void cosNormalizesNegativeAnglesLessThanMinusPi() {
        assertEquals(Math.cos(-4.0), new Cos().calculate(-4.0, EPS), ASSERT_EPS);
    }

    @Test
    void functionsRejectNonPositiveEpsilon() {
        assertThrows(IllegalArgumentException.class, () -> new Sin().calculate(1.0, 0.0));
        assertThrows(IllegalArgumentException.class, () -> new Cos().calculate(1.0, -1.0));
        assertThrows(IllegalArgumentException.class, () -> new Ln().calculate(1.0, 0.0));
    }

    @Test
    void tanReturnsNaNWhenCosineIsZero() {
        Tan tan = new Tan((x, eps) -> 1.0, (x, eps) -> 0.0);
        assertTrue(Double.isNaN(tan.calculate(1.0, EPS)));
    }

    @Test
    void tanReturnsNaNWhenDependencyReturnsNaN() {
        Tan tan = new Tan((x, eps) -> Double.NaN, (x, eps) -> 1.0);
        assertTrue(Double.isNaN(tan.calculate(1.0, EPS)));
    }

    @Test
    void tanReturnsNaNWhenCosineDependencyReturnsNaN() {
        Tan tan = new Tan((x, eps) -> 1.0, (x, eps) -> Double.NaN);
        assertTrue(Double.isNaN(tan.calculate(1.0, EPS)));
    }

    @Test
    void secReturnsNaNWhenCosineIsZeroOrNaN() {
        assertTrue(Double.isNaN(new Sec((x, eps) -> 0.0).calculate(1.0, EPS)));
        assertTrue(Double.isNaN(new Sec((x, eps) -> Double.NaN).calculate(1.0, EPS)));
    }

    @Test
    void cscReturnsNaNWhenSineIsZeroOrNaN() {
        assertTrue(Double.isNaN(new Csc((x, eps) -> 0.0).calculate(1.0, EPS)));
        assertTrue(Double.isNaN(new Csc((x, eps) -> Double.NaN).calculate(1.0, EPS)));
    }

    @Test
    void lnHandlesInvalidAndSpecialArguments() {
        Ln ln = new Ln();
        assertTrue(Double.isNaN(ln.calculate(0.0, EPS)));
        assertEquals(0.0, ln.calculate(1.0, EPS));
    }

    @Test
    void log5HandlesInvalidArgumentsAndBrokenBase() {
        Log5 log5 = new Log5(new Ln());
        assertTrue(Double.isNaN(log5.calculate(0.0, EPS)));

        Log5 broken = new Log5((x, eps) -> x == 5.0 ? 0.0 : 1.0);
        assertTrue(Double.isNaN(broken.calculate(2.0, EPS)));
    }

    @Test
    void log5ReturnsNaNWhenDependencyReturnsNaN() {
        assertTrue(Double.isNaN(new Log5((x, eps) -> Double.NaN).calculate(2.0, EPS)));
    }

    @Test
    void log5ReturnsNaNWhenArgumentValueIsNaNButBaseIsValid() {
        Log5 log5 = new Log5((x, eps) -> x == 5.0 ? 1.0 : Double.NaN);
        assertTrue(Double.isNaN(log5.calculate(2.0, EPS)));
    }

    @Test
    void log5ReturnsNaNWhenBaseValueIsNaNButArgumentIsValid() {
        Log5 log5 = new Log5((x, eps) -> x == 5.0 ? Double.NaN : 1.0);
        assertTrue(Double.isNaN(log5.calculate(2.0, EPS)));
    }

    @Test
    void log10HandlesInvalidArgumentsAndBrokenBase() {
        Log10 log10 = new Log10(new Ln());
        assertTrue(Double.isNaN(log10.calculate(0.0, EPS)));

        Log10 broken = new Log10((x, eps) -> x == 10.0 ? 0.0 : 1.0);
        assertTrue(Double.isNaN(broken.calculate(2.0, EPS)));
    }

    @Test
    void log10ReturnsNaNWhenDependencyReturnsNaN() {
        assertTrue(Double.isNaN(new Log10((x, eps) -> Double.NaN).calculate(2.0, EPS)));
    }

    @Test
    void log10ReturnsNaNWhenArgumentValueIsNaNButBaseIsValid() {
        Log10 log10 = new Log10((x, eps) -> x == 10.0 ? 1.0 : Double.NaN);
        assertTrue(Double.isNaN(log10.calculate(2.0, EPS)));
    }

    @Test
    void log10ReturnsNaNWhenBaseValueIsNaNButArgumentIsValid() {
        Log10 log10 = new Log10((x, eps) -> x == 10.0 ? Double.NaN : 1.0);
        assertTrue(Double.isNaN(log10.calculate(2.0, EPS)));
    }

    @Test
    void systemReturnsNaNWhenTrigDependenciesReturnNaN() {
        SystemFunction system = new SystemFunction(
                (x, eps) -> Double.NaN,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0
        );

        assertTrue(Double.isNaN(system.calculate(-1.0, EPS)));
    }

    @Test
    void systemReturnsNaNWhenCosDependencyReturnsNaN() {
        SystemFunction system = new SystemFunction(
                (x, eps) -> 1.0,
                (x, eps) -> Double.NaN,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0
        );

        assertTrue(Double.isNaN(system.calculate(-1.0, EPS)));
    }

    @Test
    void systemReturnsNaNWhenLastTrigDependencyReturnsNaN() {
        SystemFunction system = new SystemFunction(
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> Double.NaN,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0
        );

        assertTrue(Double.isNaN(system.calculate(-1.0, EPS)));
    }

    @Test
    void systemReturnsNaNWhenTanDependencyReturnsNaN() {
        SystemFunction system = new SystemFunction(
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> Double.NaN,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0
        );

        assertTrue(Double.isNaN(system.calculate(-1.0, EPS)));
    }

    @Test
    void systemReturnsNaNWhenSecDependencyReturnsNaN() {
        SystemFunction system = new SystemFunction(
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> Double.NaN,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0
        );

        assertTrue(Double.isNaN(system.calculate(-1.0, EPS)));
    }

    @Test
    void systemReturnsNaNWhenTrigDenominatorIsZero() {
        SystemFunction system = new SystemFunction(
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0
        );

        assertTrue(Double.isNaN(system.calculate(-1.0, EPS)));
    }

    @Test
    void systemReturnsNaNWhenTanValueIsZeroButPrimaryDenominatorIsNot() {
        SystemFunction system = new SystemFunction(
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 0.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0
        );

        assertTrue(Double.isNaN(system.calculate(-1.0, EPS)));
    }

    @Test
    void systemReturnsNaNWhenLogDependenciesReturnNaN() {
        SystemFunction system = new SystemFunction(
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> Double.NaN,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0
        );

        assertTrue(Double.isNaN(system.calculate(2.0, EPS)));
    }

    @Test
    void systemReturnsNaNWhenLastLogDependencyReturnsNaN() {
        SystemFunction system = new SystemFunction(
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> Double.NaN
        );

        assertTrue(Double.isNaN(system.calculate(2.0, EPS)));
    }

    @Test
    void systemReturnsNaNWhenMiddleLogDependencyReturnsNaN() {
        SystemFunction system = new SystemFunction(
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> Double.NaN,
                (x, eps) -> 1.0
        );

        assertTrue(Double.isNaN(system.calculate(2.0, EPS)));
    }

    @Test
    void systemReturnsNaNWhenLogDenominatorIsZero() {
        SystemFunction system = new SystemFunction(
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 2.0,
                (x, eps) -> 0.0,
                (x, eps) -> 1.0
        );

        assertTrue(Double.isNaN(system.calculate(2.0, EPS)));
    }

    @Test
    void systemReturnsNaNWhenLnValueIsZero() {
        SystemFunction system = new SystemFunction(
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0,
                (x, eps) -> 0.0,
                (x, eps) -> 1.0,
                (x, eps) -> 1.0
        );

        assertTrue(Double.isNaN(system.calculate(2.0, EPS)));
    }
}
