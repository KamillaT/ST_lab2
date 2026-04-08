package functions;

public abstract class AbstractFunction implements Function {

    protected void validateEps(double eps) {
        if (eps <= 0) {
            throw new IllegalArgumentException("Epsilon must be positive");
        }
    }
}
