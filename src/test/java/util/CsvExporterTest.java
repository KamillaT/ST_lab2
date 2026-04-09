package util;

import functions.Function;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CsvExporterTest {

    @Test
    void exportsHeaderAndDataRows() throws IOException {
        CsvExporter exporter = new CsvExporter();
        Function function = (x, eps) -> x * x;
        Path file = Files.createTempFile("system-function", ".csv");

        exporter.export(function, -1.0, 1.0, 1.0, 1e-6, file.toString(), ";");

        List<String> lines = Files.readAllLines(file);
        assertEquals(List.of(
                "x;result",
                "-1.0;1.0",
                "0.0;0.0",
                "1.0;1.0"
        ), lines);
    }

    @Test
    void rejectsNonPositiveStep() {
        CsvExporter exporter = new CsvExporter();

        assertThrows(IllegalArgumentException.class,
                () -> exporter.export((x, eps) -> x, 0.0, 1.0, 0.0, 1e-6, "ignored.csv", ","));
    }
}
