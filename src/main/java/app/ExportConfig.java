package app;

import java.util.HashMap;
import java.util.Map;

public class ExportConfig {

    private final String functionName;
    private final double start;
    private final double end;
    private final double step;
    private final double eps;
    private final String outputPath;
    private final String delimiter;
    private final boolean helpRequested;

    private ExportConfig(String functionName,
                         double start,
                         double end,
                         double step,
                         double eps,
                         String outputPath,
                         String delimiter,
                         boolean helpRequested) {
        this.functionName = functionName;
        this.start = start;
        this.end = end;
        this.step = step;
        this.eps = eps;
        this.outputPath = outputPath;
        this.delimiter = delimiter;
        this.helpRequested = helpRequested;
    }

    public static ExportConfig fromArgs(String[] args) {
        if (args.length == 1 && "--help".equals(args[0])) {
            return new ExportConfig("system", -5.0, 5.0, 0.1, 1e-6, "result.csv", ",", true);
        }

        Map<String, String> options = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (!arg.startsWith("--")) {
                throw new IllegalArgumentException("Arguments must use the format --key value");
            }
            if (i + 1 >= args.length) {
                throw new IllegalArgumentException("Missing value for argument: " + arg);
            }
            options.put(arg.substring(2), args[++i]);
        }

        double start = parseDouble(options.getOrDefault("start", "-5.0"), "start");
        double end = parseDouble(options.getOrDefault("end", "5.0"), "end");
        double step = parseDouble(options.getOrDefault("step", "0.1"), "step");
        double eps = parseDouble(options.getOrDefault("eps", "1e-6"), "eps");

        if (step <= 0) {
            throw new IllegalArgumentException("Step must be positive");
        }

        return new ExportConfig(
                options.getOrDefault("function", "system"),
                start,
                end,
                step,
                eps,
                options.getOrDefault("out", "result.csv"),
                options.getOrDefault("delimiter", ","),
                false
        );
    }

    public static String usage() {
        return """
                Usage:
                  java -cp target/classes Main [--function name] [--start value] [--end value]
                                              [--step value] [--eps value] [--out path]
                                              [--delimiter value]

                Example:
                  java -cp target/classes Main --function system --start -5 --end 5 --step 0.1 --eps 1e-6 --out result.csv --delimiter ,
                """;
    }

    private static double parseDouble(String value, String fieldName) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid numeric value for " + fieldName + ": " + value, e);
        }
    }

    public String functionName() {
        return functionName;
    }

    public double start() {
        return start;
    }

    public double end() {
        return end;
    }

    public double step() {
        return step;
    }

    public double eps() {
        return eps;
    }

    public String outputPath() {
        return outputPath;
    }

    public String delimiter() {
        return delimiter;
    }

    public boolean isHelpRequested() {
        return helpRequested;
    }
}
