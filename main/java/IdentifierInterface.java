/**	@elements : StringBuffer
 *	@structure : linear
 *	@domain : 	The variables of type StringBuffer are identifiers.
 *              Each StringBuffer object can contain only small letters, digits and an underscore.
 *              Spaces are not allowed.
 *              Each name starts with a small letter.
 *	@constructor - Identifier(); Identifier(char character);
 *	<dl>
 *		<dt><b>PRE-condition</b><dd>		-
 *		<dt><b>POST-condition</b><dd> 	The new Identifier object is part of the allowed set of identifiers.
 * </dl>
 **/

public interface IdentifierInterface {
    /**
     * @precondition - identifier is not null
     * @postcondition - returned identifier is the full sequence of characters given
     * until this moment
     * @return full identifier value
     */
    String retrieveName();

    /**
     * @precondition - object is not null. object is of type Identifier.
     * @postcondition - neither the calling object, nor the object parameter are changed
     *
     * @return true: if the calling object is equal to object parameter
     *          false: otherwise
     */
    boolean equals(Object object);

    /**
     * Whenever it is invoked on the same object more than once during
     * an execution of a Java application, the {@code hashCode} method
     * must consistently return the same integer, provided no information
     * used in {@code equals} comparisons on the object is modified.
     *
     * If two objects are equal according to the {@code equals(Object)}
     * method, then calling the {@code hashCode} method on each of
     * the two objects must produce the same integer result.
     * @return - Returns a hash code value for the object.
     */
    int hashCode();

}
