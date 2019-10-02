import java.math.BigInteger;

/**	@elements : objects of type BigInteger
 *	@structure : linear
 *	@domain : 	The elements in the set are sorted monotonically increasing and do not repeat.
 *				All rows of elements of type BigInteger are natural numbers.
 *	@constructor - Set();
 *	<dl>
 *		<dt><b>PRE-condition</b><dd>		-
 *		<dt><b>POST-condition</b><dd> 	The new Set object contains only non-repeating natural numbers.
 * </dl>
 **/

public interface SetInterface {
    /**
     * @precondition - set is not null; data is not already in the set
     * @postcondition - data has been inserted in the set
     * @param data - the number to be inserted
     */
    void insert(BigInteger data) throws APException;

    /**
     * @precondition - set is not null
     * @postcondition - set has not changed
     * @return - the size of the set
     */
    int size() throws APException;

    /**
     * @precondition - set is not null; otherSet is not null
     * @postcondition -  new set has been returned. The new set is a union of
     *                  the calling object Set and otherSet.
     * @param otherSet
     * @return - the new set produced of the union between calling object and otherSet
     */
    Set union(Set otherSet) throws APException;

    /**
     * @precondition - set is not null; otherSet is not null
     * @postcondition - new set has been returned. The new set is a intersection of
     *                  the calling object Set and otherSet.
     * @param otherSet
     * @return - the new set produced of the intersection between calling object and otherSet
     */
    Set intersection(Set otherSet) throws APException;

    /**
     * @precondition - set is not null; otherSet is not null
     * @postcondition - new set has been returned. The new set is a complement of
     *                  the calling object Set and otherSet.
     * @param otherSet
     * @return - calling object complement of otherSet
     */
    Set complement(Set otherSet) throws APException;

    /**
     * @precondition - set is not null; otherSet is not null
     * @postcondition - new set has been returned. The new set is a symmetric difference of
     *                  the calling object Set and otherSet.
     * @param otherSet
     * @return - the new set produced of the symmetric difference between calling object and otherSet
     */
    Set symmetric_difference(Set otherSet) throws APException;

    /**
     * @precondition - set is not null;
     * @postcondition - the elements of the set are returned in order
     * @return - a String containing the elements of the set
     */
    String toString();
}
