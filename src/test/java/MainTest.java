import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {

    @Test
    void mainClassCanBeInstantiated() {
        new Main();
    }

    @Test
    void printsHelpWhenRequested() {
        OutputCapture capture = captureOutput();
        try {
            Main.main(new String[]{"--help"});
        } finally {
            capture.restore();
        }

        assertTrue(capture.stdoutText().contains("Usage:"));
        assertTrue(capture.stdoutText().contains("Available functions:"));
    }

    @Test
    void printsErrorForUnknownFunction() {
        OutputCapture capture = captureOutput();
        try {
            Main.main(new String[]{"--function", "unknown"});
        } finally {
            capture.restore();
        }

        assertTrue(capture.stderrText().contains("Unknown function: unknown"));
    }

    @Test
    void printsUsageForInvalidArguments() {
        OutputCapture capture = captureOutput();
        try {
            Main.main(new String[]{"function"});
        } finally {
            capture.restore();
        }

        assertTrue(capture.stderrText().contains("Arguments must use the format --key value"));
        assertTrue(capture.stdoutText().contains("Usage:"));
    }

    @Test
    void exportsRequestedFunction() throws Exception {
        Path file = Files.createTempFile("main-export", ".csv");
        OutputCapture capture = captureOutput();
        try {
            Main.main(new String[]{
                    "--function", "sin",
                    "--start", "-1",
                    "--end", "1",
                    "--step", "1",
                    "--out", file.toString()
            });
        } finally {
            capture.restore();
        }

        assertTrue(capture.stdoutText().contains("CSV file generated"));
        assertTrue(Files.readString(file).contains("x,result"));
    }

    @Test
    void printsStackTraceWhenExportFails() throws Exception {
        Path dir = Files.createTempDirectory("main-export-dir");
        OutputCapture capture = captureOutput();
        try {
            Main.main(new String[]{
                    "--function", "sin",
                    "--out", dir.toString()
            });
        } finally {
            capture.restore();
        }

        assertTrue(capture.stderrText().contains("FileNotFoundException") || capture.stderrText().contains("IOException"));
    }

    private static OutputCapture captureOutput() {
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        ByteArrayOutputStream stderr = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        PrintStream oldErr = System.err;
        System.setOut(new PrintStream(stdout));
        System.setErr(new PrintStream(stderr));
        return new OutputCapture(oldOut, oldErr, stdout, stderr);
    }

    private record OutputCapture(PrintStream oldOut,
                                 PrintStream oldErr,
                                 ByteArrayOutputStream stdout,
                                 ByteArrayOutputStream stderr) {
        void restore() {
            System.setOut(oldOut);
            System.setErr(oldErr);
        }

        String stdoutText() {
            return stdout.toString(StandardCharsets.UTF_8);
        }

        String stderrText() {
            return stderr.toString(StandardCharsets.UTF_8);
        }
    }
}
