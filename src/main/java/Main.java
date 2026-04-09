import app.ExportConfig;
import app.FunctionRegistry;
import functions.Function;
import util.CsvExporter;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        ExportConfig config;
        try {
            config = ExportConfig.fromArgs(args);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            System.out.println(ExportConfig.usage());
            return;
        }

        if (config.isHelpRequested()) {
            System.out.println(ExportConfig.usage());
            System.out.println("Available functions: " + FunctionRegistry.availableFunctionNames());
            return;
        }

        Map<String, Function> functions = FunctionRegistry.createFunctions();
        Function targetFunction = functions.get(config.functionName());

        if (targetFunction == null) {
            System.err.println("Unknown function: " + config.functionName());
            System.out.println("Available functions: " + FunctionRegistry.availableFunctionNames());
            return;
        }

        CsvExporter exporter = new CsvExporter();

        try {
            exporter.export(
                    targetFunction,
                    config.start(),
                    config.end(),
                    config.step(),
                    config.eps(),
                    config.outputPath(),
                    config.delimiter()
            );
            System.out.println("CSV file generated: " + config.outputPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
