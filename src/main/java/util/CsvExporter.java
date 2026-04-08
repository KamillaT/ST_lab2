package util;

import functions.Function;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CsvExporter {

    public void export(Function function,
                       double start,
                       double end,
                       double step,
                       double eps,
                       String filePath,
                       String delimiter) throws IOException {

        if (step <= 0) {
            throw new IllegalArgumentException("Step must be positive");
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("x" + delimiter + "result");

            for (double x = start; x <= end; x += step) {
                double result = function.calculate(x, eps);
                writer.println(x + delimiter + result);
            }
        }
    }
}