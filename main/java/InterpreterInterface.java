/**
 * This interface is to serve the purpose of
 * describing the syntax-portion of the command-
 * language grammar, which is to be used in Assignment2.
 * The methods are first described in EBNF-notation and then
 * the semantics in English.
 */

import java.math.BigInteger;
import java.util.Scanner;

/**	@elements : Identifier, Set<E>
 *	@structure : linear
 *	@domain : 	The variables of type Identifier are identifiers.
 *              The variables of type Set are sets.
 *	@constructor - Interpreter(Scanner line)
 *	<dl>
 *		<dt><b>PRE-condition</b><dd>		-
 *		<dt><b>POST-condition</b><dd> 	The new Interpreter object is
 *	                                    to be used as a pointer for all of its methods.
 * </dl>
 **/


public interface InterpreterInterface {

    /**
     * @precondition - Input is not an empty line.
     * @postcondition - If there is end-of-line after input, the result is printed on the screen
     * program = { statement } <eof> ;
     * A program is any arbitrary number of statements (commands) ended by the
     end of file.
     */
    void program(Scanner input);

    /**
     * @precondition - There is a non-empty line inputted from keyboard.
     * @postcondition - Tbe correct result to be printed on the screen is produced and saved
     * statement = assignment | print_statement | comment ;
     * A statement is an assignment-statement, a print-statement or a comment-line.
     */
    void statement(Scanner input) throws APException;


    /**
     * @precondition - The first character in the input is a letter.
     * @postcondition - An identifier is extracted.
     *                  Expression is evaluated and a set is returned.
     *                  Identifier and Set are added to the hashMap
     * assignment = identifier '=' expression <eoln> ;
     * An assignment statement is an identifier, followed by the '=' sign, after which
     there is an expression, followed by an end-of-line.
     */
    void assignment(Scanner input) throws APException;

    /**
     * @precondition - The first character in the input is a '?'
     * @postcondition - Expression is evaluated and set is returned.
     *                  Returned set is produced as output.
     * print_statement = '?' expression <eoln> ;
     * A print statement is a '?' followed by an expression and ended with an end-offline.
     */
    void print_statement(Scanner input) throws APException;

    /**
     * @precondition - The first character in the input is '/'
     * @postcondition - No output is produced on the screen.
     * comment = '/' <a line of text> <eoln> ;
     * A comment is a line of text that starts with the '/' -sign and is closed by an
     end-of-line.
     */
    void comment(Scanner input) throws APException;

    /**
     * @precondition - Input starts with a letter.
     * @postcondition - New identifier object is created and returned.
     * identifier = letter { letter | number } ;
     * An identifier starts with a letter and only consists of letters and numbers.
     */
    Identifier identifier(Scanner input) throws APException;

    /**
     * @precondition - Expression consists of at least one term.
     * @postcondition - Terms are evaluated with additive_operator, if there is any.
     *                  A set is returned as a result of the expression.
     * expression = term { additive_operator term } ;
     * An expression is a term, followed by zero or more terms. All terms are
     * separated by an additive-operator.
     */
    Set expression(Scanner input) throws APException;

    /**
     * @precondition - Term consists of at least one factor.
     * @postcondition - Factors are evaluated with multiplicative_operator, if there is any.
     *                  A set is returned as a result of the term.
     * term = factor { multiplicative_operator factor } ;
     * A term is a factor, followed by 0 or more factors. All factors are separated by
     * a multiplicative-operator.
     */
    Set term(Scanner input) throws APException;

    /**
     * @precondition - A factor starts with a letter, a '(' or a '{'
     * @postcondition - If it is an identifier: the set as a Value in the hashMap
     *                   with Key 'this identifier', is returned.
     *                   If it is a complex_factor: the set as a result of the
     *                   expression in complex_factor is returned.
     *                   If it is a set: the set is returned.
     * factor = identifier | complex_factor | set ;
     * A factor is an identifier, a complex factor or a set.
     */
    Set factor(Scanner input) throws APException;

    /**
     * @precondition - Input starts with a '(' and ends with a ')'.
     * @postcondition - The set as a result of expression is returned.
     * complex_factor = '(' expression ')' ;
     * A complex factor is an expression between round brackets.
     */
    Set complex_factor(Scanner input) throws APException;

    /**
     * @precondition - Input starts with a '{' and ends with a '}'
     * @postcondition - The set inside {}.
     * set = '{' row_natural_numbers '}' ;
     * A set is a row of natural numbers between accolades.
     */
    Set set(Scanner input) throws APException;

    /**
     * @precondition - Input has at least one natural_number
     * @postcondition - The set of natural numbers is returned.
     * row_natural_numbers = [ natural_number { ',' natural_number } ] ;
     * A row of natural numbers is empty or a summation of one or more natural
     * numbers separated by commas.
     */
    Set row_natural_numbers(Scanner input) throws APException;

    /**
     * @precondition - Next character in input is '+', '|' or a '−'
     * @postcondition - TRUE: if precondition is true
     *                  FALSE: otherwise
     * additive_operator = '+' | '|' | '−' ;
     * An additive operator is a '+', a '|' or a '-' sign.
     */
    boolean additive_operator(Scanner input) throws APException;

    /**
     * @precondition - Next character in input is '*'
     * @postcondition - TRUE: if precondition is true
     *                  FALSE: otherwise
     * multiplicative_operator = '*' ;
     * A multiplicative operation is a '*' -sign.
     */
    boolean multiplicative_operator(Scanner input) throws APException;

    /**
     * @precondition - Next String in input is a natural number.
     * @postcondition - The value of the next String is returned as a BigInteger.
     * natural_number = positive_number | zero ;
     * A natural number is a positive number or zero.
     */
    BigInteger natural_number(Scanner input) throws APException;

    /**
     * @precondition - Next String in input is a number, which does not start with a zero.
     * @postcondition - The value of the next String is returned as a BigInteger.
     * positive_number = not_zero { number } ;
     * A positive number does not start with a zero, does not have a sign, and has 1
     * or more numbers.
     */
    BigInteger positive_number(Scanner input) throws APException;

    /**
     * @precondition - Next char in input is a digit 0-9
     * @postcondition - TRUE: if precondition is true.
     *                  FALSE: otherwise.
     * number = zero | not_zero ;
     * A number is a zero or not a zero.
     */
    boolean number(Scanner input);

    /**
     * @precondition - Next char in input is the digit 0
     * @postcondition - TRUE: if precondition is true.
     *                  FALSE: otherwise.
     * zero = '0' ;
     * Zero is the number 0.
     */
    boolean zero(Scanner input);

    /**
     * @precondition - Next char in input is a digit 1-9
     * @postcondition - TRUE: if precondition is true.
     *                  FALSE: otherwise.
     * not_zero = '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9' ;
     * Not-zero is the number from the range 1 up to and including 9.
     */
    boolean not_zero(Scanner input);

    /**
     * @precondition - Next char in input is a letter
     * @postcondition - TRUE: if precondition is true.
     *                  FALSE: otherwise.
     * letter = 'A' | 'B' | 'C' | 'D' | 'E' | 'F' | 'G' | 'H' | 'I' | 'J' | 'K' | 'L' | 'M' |
     'N' | 'O' | 'P' | 'Q' | 'R' | 'S' | 'T' | 'U' | 'V' | 'W' | 'X' | 'Y' | 'Z' |
     'a' | 'b' | 'c' | 'd' | 'e' | 'f' | 'g' | 'h' | 'i' | 'j' | 'k' | 'l' | 'm' |
     'n' | 'o' | 'p' | 'q' | 'r' | 's' | 't' | 'u' | 'v' | 'w' | 'x' | 'y' | 'z' ;
     * A letter is a capital letter or a small letter.
     * @return
     */
    boolean letter(Scanner input);

}
