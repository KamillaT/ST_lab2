package app;

import functions.Function;
import functions.log.Ln;
import functions.log.Log10;
import functions.log.Log5;
import functions.system.SystemFunction;
import functions.trig.Cos;
import functions.trig.Csc;
import functions.trig.Sec;
import functions.trig.Sin;
import functions.trig.Tan;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;

public final class FunctionRegistry {

    private FunctionRegistry() {
    }

    public static Map<String, Function> createFunctions() {
        Map<String, Function> functions = new LinkedHashMap<>();

        Function sin = new Sin();
        Function cos = new Cos();
        Function tan = new Tan(sin, cos);
        Function sec = new Sec(cos);
        Function csc = new Csc(sin);

        Function ln = new Ln();
        Function log5 = new Log5(ln);
        Function log10 = new Log10(ln);

        functions.put("sin", sin);
        functions.put("cos", cos);
        functions.put("tan", tan);
        functions.put("sec", sec);
        functions.put("csc", csc);
        functions.put("ln", ln);
        functions.put("log5", log5);
        functions.put("log10", log10);
        functions.put("system", new SystemFunction(
                sin, cos, tan, sec, csc,
                ln, log5, log10
        ));

        return functions;
    }

    public static String availableFunctionNames() {
        StringJoiner joiner = new StringJoiner(", ");
        for (String name : createFunctions().keySet()) {
            joiner.add(name);
        }
        return joiner.toString();
    }
}
