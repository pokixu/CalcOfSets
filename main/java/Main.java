import java.io.PrintStream;
import java.util.Scanner;

/**
 * This program was created by:
 * Polina Boneva - pba590
 * CS, Year 2
 */

public class Main {
    private void start() {
        Scanner in = new Scanner(System.in);
        Interpreter interpreter = new Interpreter();
        interpreter.program(in);
        in.close();
    }

    public static void main(String[] argv) {
        new Main().start();
    }
}
