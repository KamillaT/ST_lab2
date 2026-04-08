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
import util.CsvExporter;

public class Main {

    public static void main(String[] args) {
        double eps = 1e-6;

        Function sin = new Sin();
        Function cos = new Cos();
        Function tan = new Tan(sin, cos);
        Function sec = new Sec(cos);
        Function csc = new Csc(sin);

        Function ln = new Ln();
        Function log5 = new Log5(ln);
        Function log10 = new Log10(ln);

        Function system = new SystemFunction(
                sin, cos, tan, sec, csc,
                ln, log5, log10
        );

        CsvExporter exporter = new CsvExporter();

        try {
            exporter.export(system, -5.0, 5.0, 0.1, eps, "result.csv", ",");
            System.out.println("CSV file generated: result.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}