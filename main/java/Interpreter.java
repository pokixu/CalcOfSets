import java.io.PrintStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Interpreter implements InterpreterInterface {
    private PrintStream out;
    private String output;
    private HashMap<Identifier, Set> hashMap;

    Interpreter() {
        out = new PrintStream(System.out);
        hashMap = new HashMap<>();
    }

    // program = { statement } <eof> ;
    @Override
    public void program(Scanner input) {
        do {
            try {
                String lineStr = input.nextLine();
                Scanner line = new Scanner(lineStr);
                line.useDelimiter("");
                statement(line);
                if (output != null) {
                    out.println(output);
                    output = null;
                }
            }
            catch(APException e){
                out.println(e.getMessage());
            }
        }
        while (input.hasNextLine()) ;

    }

    // statement = assignment | print_statement | comment ;
    @Override
    public void statement(Scanner line) throws APException {
        skipSpaces(line);
        if (letter(line)) {
            assignment(line);
        }
        else if (nextCharIs(line, '?')) {
            print_statement(line);
        }
        else if (nextCharIs(line, '/')) {
            comment(line);
        }
        else {
            throw new APException("identifier, ?, or / expected");
        }
    }

    // assignment = identifier '=' expression <eoln> ;
    @Override
    public void assignment(Scanner line) throws APException {
        Identifier identifier = identifier(line);
        skipSpaces(line);
        character(line, '=');
        skipSpaces(line);
        Set set = expression(line);
        skipSpaces(line);
        eoln(line);
        hashMap.put(identifier, set);
    }

    // print_statement = '?' expression <eoln> ;
    @Override
    public void print_statement(Scanner line) throws APException {
        character(line, '?');
        skipSpaces(line);
        Set set = expression(line);
        skipSpaces(line);
        eoln(line);
        output = set.toString();
    }

    // comment = '/' <a line of text> <eoln> ;
    @Override
    public void comment(Scanner line) throws APException {
        character(line, '/');
    }

    // identifier = letter { letter | number } ;
    @Override
    public Identifier identifier(Scanner line) throws APException {
        StringBuilder id = new StringBuilder("");
        do {
            if (!(letter(line) || number(line))) {
                throw new APException("invalid identifier");
            }
            char nextChar = nextChar(line);
            id.append(nextChar);
        }
        while ((!(nextCharIs(line, ' ') ||
                nextCharIs(line, '=') ||
                nextCharIs(line, ')') ||
                additive_operator(line) ||
                multiplicative_operator(line)))
                && line.hasNext());
        return new Identifier(id.toString());
    }

    // expression = term { additive_operator term } ;
    @Override
    public Set expression(Scanner line) throws APException {
        Set set = term(line);
        skipSpaces(line);
        while (additive_operator(line)) {
            char operator = nextChar(line);
            skipSpaces(line);
            if (!line.hasNext()) {
                throw new APException("unfinished expression");
            }
            switch (operator) {
                case '+':
                    set = set.union(term(line));
                    break;
                case '|':
                    set = set.symmetric_difference(term(line));
                    break;
                case '-':
                    set = set.complement(term(line));
                    break;
                default:
                    throw new APException("unsupported operation");
            }
            skipSpaces(line);
        }
        return set;
    }

    // term = factor { multiplicative_operator factor } ;
    @Override
    public Set term(Scanner line) throws APException {
        Set set = factor(line);
        skipSpaces(line);
        while (multiplicative_operator(line)) {
            character(line, '*');
            skipSpaces(line);
            if (!line.hasNext()) {
                throw new APException("unfinished expression");
            }
            set = set.intersection(factor(line));
            skipSpaces(line);
        }
        return set;
    }

    // factor = identifier | complex_factor | set ;
    @Override
    public Set factor(Scanner line) throws APException {
        Set set;
        if (letter(line)) {
            Identifier identifier = identifier(line);
            if (!hashMap.containsKey(identifier)) {
                throw new APException("identifier not found");
            }
            set = hashMap.get(identifier);
        }
        else if (nextCharIs(line, '(')) {
            set = complex_factor(line);
        }
        else if (nextCharIs(line, '{')) {
            set = set(line);
        }
        else {
            throw new APException("identifier, expression (), or a set {} expected after '='");
        }
        return set;
    }

    // complex_factor = '(' expression ')' ;
    @Override
    public Set complex_factor(Scanner line) throws APException {
        character(line, '(');
        skipSpaces(line);
        Set set = expression(line);
        skipSpaces(line);
        character(line, ')');
        return set;
    }

    // set = '{' row_natural_numbers '}' ;
    @Override
    public Set set(Scanner line) throws APException {
        character(line, '{');
        skipSpaces(line);
        Set set = row_natural_numbers(line);
        skipSpaces(line);
        character(line, '}');
        return set;
    }

    // row_natural_numbers = [ natural_number { ',' natural_number } ] ;
    @Override
    public Set row_natural_numbers(Scanner line) throws APException {
        Set set = new Set();
        if (!nextCharIs(line, '}')) {
            set.insert(natural_number(line));
            skipSpaces(line);
        }
        while (!nextCharIs(line, '}') && line.hasNext()) {
            character(line, ',');
            skipSpaces(line);
            set.insert(natural_number(line));
            skipSpaces(line);
        }
        return set;
    }

    // additive_operator = '+' | '|' | '−' ;
    @Override
    public boolean additive_operator(Scanner line) throws APException {
        return (nextCharIs(line, '+') ||
                nextCharIs(line, '|') ||
                nextCharIs(line, '-'));
    }

    // multiplicative_operator = '*' ;
    @Override
    public boolean multiplicative_operator(Scanner line) throws APException {
        return nextCharIs(line, '*');
    }

    // natural_number = positive_number | zero ;
    @Override
    public BigInteger natural_number(Scanner line) throws APException {
        BigInteger result;
        if (!zero(line)) {
            result = positive_number(line);
        }
        else {
            result = new BigInteger(line.next());
            if (number(line)) {
                throw new APException("number cannot start with a 0");
            }
        }
        return result;
    }

    // positive_number = not_zero { number } ;
    @Override
    public BigInteger positive_number(Scanner line) throws APException {
        if (!not_zero(line)) {
            throw new APException("number has to start with 1-9");
        }

        StringBuilder pos_number = new StringBuilder("");
        // Take numbers until character after a number is ',', '}' or a ' '.
        while ((!(nextCharIs(line, ',') ||
                nextCharIs(line, '}') ||
                nextCharIs(line, ' ')))
                && line.hasNext()) {
            if (!number(line)) {
                throw new APException("number can contain only 0-9");
            }
            pos_number.append(nextChar(line));
        }
        return new BigInteger(pos_number.toString());
    }

    // number = zero | not_zero ;
    @Override
    public boolean number(Scanner line) {
        return zero(line) || not_zero(line);
    }

    // zero = '0' ;
    @Override
    public boolean zero(Scanner line) {
        return line.hasNext("0");
    }

    // not_zero = '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9' ;
    @Override
    public boolean not_zero(Scanner line) {
        return line.hasNext("[1-9]");
    }

    /*
    letter = 'A' | 'B' | 'C' | 'D' | 'E' | 'F' | 'G' | 'H' | 'I' | 'J' | 'K' | 'L' | 'M' |
    'N' | 'O' | 'P' | 'Q' | 'R' | 'S' | 'T' | 'U' | 'V' | 'W' | 'X' | 'Y' | 'Z' |
    'a' | 'b' | 'c' | 'd' | 'e' | 'f' | 'g' | 'h' | 'i' | 'j' | 'k' | 'l' | 'm' |
    'n' | 'o' | 'p' | 'q' | 'r' | 's' | 't' | 'u' | 'v' | 'w' | 'x' | 'y' | 'z' ;
    */
    @Override
    public boolean letter(Scanner line) {
        return line.hasNext("[a-zA-Z]");
    }

    // Throws an exception if there is no end-of-line when line should be over
    private void eoln(Scanner line) throws APException {
        if (line.hasNext()) {
            throw new APException("no end-of-line");
        } else {
            return;
        }
    }

    // Takes in a single character from line. Throws an Exception if it is not the expected character.
    private void character(Scanner line, char c) throws APException{
        if(!nextCharIs(line, c)) {
            throw new APException("'" + c + "'" + " expected");
        }
        nextChar(line);
    }

    // Checks if the nect character is the expected one.
    private boolean nextCharIs(Scanner line, char c) {
        return line.hasNext(Pattern.quote(c + ""));
    }

    // Moves past one character from line.
    private char nextChar(Scanner line) {
        return line.next().charAt(0);
    }

    // Moves past all characters which are an empty space ' '
    private void skipSpaces(Scanner line) {
        while (nextCharIs(line, ' ')) {
            nextChar(line);
        }
    }

}
