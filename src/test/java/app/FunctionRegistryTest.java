package app;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FunctionRegistryTest {

    @Test
    void exposesAllRequiredModules() {
        Map<String, ?> functions = FunctionRegistry.createFunctions();

        assertTrue(functions.containsKey("sin"));
        assertTrue(functions.containsKey("cos"));
        assertTrue(functions.containsKey("tan"));
        assertTrue(functions.containsKey("sec"));
        assertTrue(functions.containsKey("csc"));
        assertTrue(functions.containsKey("ln"));
        assertTrue(functions.containsKey("log5"));
        assertTrue(functions.containsKey("log10"));
        assertTrue(functions.containsKey("system"));
    }

    @Test
    void listsAllFunctionNames() {
        assertEquals("sin, cos, tan, sec, csc, ln, log5, log10, system",
                FunctionRegistry.availableFunctionNames());
    }
}
