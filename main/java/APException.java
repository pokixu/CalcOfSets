public class APException extends Exception {

    private static final long serialVersionUID = 1L;

    public APException() {
        super("Exception. Please give valid input.");
    }

    public APException (String s) {
        super(s);
    }

}