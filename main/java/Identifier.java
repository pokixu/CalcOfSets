import java.util.Objects;

public class Identifier implements IdentifierInterface {
    private String identifier;

    /**
     * Constructor. Takes String to create an identifier
     * @param id - the identifier name
     */
    Identifier(String id) {
        identifier = id;
    }

    public String retrieveName(){
        return identifier;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Identifier)) return false;
        return Objects.equals(this.retrieveName(), ((Identifier) object).retrieveName());
    }

    public int hashCode(){
        int result = 17;
        return 31 * result + retrieveName().hashCode();
    }
}
