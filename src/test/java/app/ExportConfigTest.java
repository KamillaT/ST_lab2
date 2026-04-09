package app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExportConfigTest {

    @Test
    void parsesCustomArguments() {
        ExportConfig config = ExportConfig.fromArgs(new String[]{
                "--function", "sin",
                "--start", "-3.14",
                "--end", "3.14",
                "--step", "0.2",
                "--eps", "1e-5",
                "--out", "sin.csv",
                "--delimiter", ";"
        });

        assertEquals("sin", config.functionName());
        assertEquals(-3.14, config.start());
        assertEquals(3.14, config.end());
        assertEquals(0.2, config.step());
        assertEquals(1e-5, config.eps());
        assertEquals("sin.csv", config.outputPath());
        assertEquals(";", config.delimiter());
    }

    @Test
    void supportsHelpFlag() {
        ExportConfig config = ExportConfig.fromArgs(new String[]{"--help"});

        assertTrue(config.isHelpRequested());
    }

    @Test
    void providesUsageText() {
        assertTrue(ExportConfig.usage().contains("Usage:"));
    }

    @Test
    void rejectsMissingValue() {
        assertThrows(IllegalArgumentException.class, () -> ExportConfig.fromArgs(new String[]{"--function"}));
    }

    @Test
    void rejectsArgumentsWithoutOptionPrefix() {
        assertThrows(IllegalArgumentException.class, () -> ExportConfig.fromArgs(new String[]{"function", "sin"}));
    }

    @Test
    void rejectsInvalidNumericValues() {
        assertThrows(IllegalArgumentException.class,
                () -> ExportConfig.fromArgs(new String[]{"--start", "oops"}));
    }

    @Test
    void rejectsNonPositiveStep() {
        assertThrows(IllegalArgumentException.class,
                () -> ExportConfig.fromArgs(new String[]{"--step", "0"}));
    }
}
