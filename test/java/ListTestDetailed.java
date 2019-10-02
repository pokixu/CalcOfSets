import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

// Uses List<BigInteger>
public class ListTestDetailed {

    //the tests rely on each-other; if one fails, that means multiple things might be wrong
    //so check the methods used inside it

    @Test
    public void isEmpty_isCorrect() {
        ListInterface<BigInteger> list = new List<>();
        assertTrue("List should be empty but is not.", list.isEmpty());

        list.insert(BigInteger.ONE);
        assertFalse("List should not be empty but is.", list.isEmpty());
    }

    @Test
    public void init_emptiesList() {
        ListInterface<BigInteger> list = new List<>();
        BigInteger bigInteger1 = BigInteger.valueOf(123);
        BigInteger bigInteger2 = BigInteger.valueOf(456);
        list.insert(bigInteger1).insert(bigInteger2);

        ListInterface<BigInteger> returnList = list.init();
        assertTrue("List should be empty but is not.", list.isEmpty());
        assertTrue("List returned should be empty but is not.", returnList.isEmpty());
    }

    @Test
    public void init_returnsSameList() {
        ListInterface<BigInteger> list = new List<>();
        ListInterface<BigInteger> returnList1 = list.init();

        assertSame("Returned list should be the same.", list, returnList1);

        list.insert(BigInteger.ONE);
        ListInterface<BigInteger> returnList2 = list.init();

        assertSame("Returned list should be the same.", list, returnList2);
    }

    @Test
    public void size_isCorrect() {
        ListInterface<BigInteger> list = new List<>();

        assertEquals("Empty list should have size 0 but does not.", 0, list.size());

        list.insert(BigInteger.ONE);
        assertEquals("List with 1 element should have size 1 but does not.", 1, list.size());

        list.insert(BigInteger.TEN);
        assertEquals("List with 2 elements should have size 2 but does not.", 2, list.size());
    }

    @Test
    public void insert_isAdded() {
        ListInterface<BigInteger> list = new List<>();
        BigInteger element = BigInteger.ONE;

        list.insert(element);
        assertEquals("Adding one element should mean size == 1, but is not.", 1, list.size());
        assertTrue("List does not find the element.", list.find(element));
    }

    @Test
    public void insert_duplicateIsAdded() {
        ListInterface<BigInteger> list = new List<>();
        BigInteger element = BigInteger.ONE;

        list.insert(element);
        list.insert(element);

        assertEquals("Duplicate element should make size == 2.", 2, list.size());
        BigInteger second = list.retrieve();
        list.goToPrevious();
        BigInteger first = list.retrieve();
        assertEquals("Elements should be equal.", first, second);
    }

    @Test
    public void insert_returnsSameList() {
        ListInterface<BigInteger> list = new List<>();
        ListInterface returnList = list.insert(BigInteger.TEN);

        assertSame("Returned list should be the same.", list, returnList);
    }

    @Test
    public void insert_isAddedSorted() {
        ListInterface<BigInteger> list = new List<>();
        BigInteger element1 = BigInteger.valueOf(2);
        BigInteger element2 = BigInteger.valueOf(1);
        BigInteger element3 = BigInteger.valueOf(4);
        BigInteger element4 = BigInteger.valueOf(3);

        list.insert(element1);
        assertTrue("List does not find first added element.", list.find(element1));

        list.insert(element2);
        list.goToFirst();
        assertEquals("Element2 is not in the right position.", element2, list.retrieve());

        list.insert(element3);
        list.goToFirst();
        list.goToNext();
        list.goToNext();
        assertEquals("Element3 is not in the right position.", element3, list.retrieve());
        list.goToPrevious();
        assertEquals("Element1 is not in the right position.", element1, list.retrieve());
        list.goToPrevious();
        assertEquals("Element2 is not in the right position.", element2, list.retrieve());

        list.insert(element4);
        list.goToFirst();
        list.goToNext();
        list.goToNext();
        assertEquals("Element4 is not in the right position.", element4, list.retrieve());
        list.goToNext();
        assertEquals("Element3 is not in the right position.", element3, list.retrieve());
        list.goToPrevious();
        list.goToPrevious();
        assertEquals("Element1 is not in the right position.", element1, list.retrieve());
        list.goToPrevious();
        assertEquals("Element2 is not in the right position.", element2, list.retrieve());
    }

    @Test
    public void insert_increasesSize() {
        ListInterface<BigInteger> list = new List<>();

        assertEquals("Initial size is 0.", 0, list.size());
        list.insert(BigInteger.ONE);
        assertEquals("Adding elements should increase size by 1.", 1, list.size());
    }

    @Test
    public void insert_elementShouldBeCopied() {
        // couldn't find a mutable class implementing comparable to test this; also
        // cannot test along retrieve because both insert and retrieve should make a copy
        // why would both/any make a copy again? I think none should
    }

    @Test
    public void retrieve_returnsElement() {
        ListInterface<BigInteger> list = new List<>();
        BigInteger bigInteger1 = BigInteger.valueOf(123);
        list.insert(bigInteger1);

        list.find(bigInteger1);

        BigInteger retrieved = list.retrieve();

        assertEquals("Retrieved element should equal inserted element.", bigInteger1, retrieved);
    }

    @Test
    public void remove_removesOnlyElement() {
        ListInterface<BigInteger> list = new List<>();
        list.insert(BigInteger.ONE);
        list.remove();

        assertTrue("Remove should remove the only element.", list.isEmpty());
    }

    @Test
    public void remove_removesLastElementCorrectly() {
        ListInterface<BigInteger> list = new List<>();
        BigInteger bigInteger1 = BigInteger.valueOf(123);
        BigInteger bigInteger2 = BigInteger.valueOf(456);

        list.insert(bigInteger1).insert(bigInteger2);

        list.find(bigInteger2);

        list.remove();

        BigInteger remainingElement = list.retrieve();

        assertEquals("Remaining element should be pointed to by current.", bigInteger1, remainingElement);
    }

    @Test
    public void remove_removesMiddleElementCorrectly() {
        ListInterface<BigInteger> list = new List<>();
        BigInteger bigInteger1 = BigInteger.valueOf(123);
        BigInteger bigInteger2 = BigInteger.valueOf(456);
        BigInteger bigInteger3 = BigInteger.valueOf(789);

        list.insert(bigInteger1).insert(bigInteger2).insert(bigInteger3);

        list.find(bigInteger2);

        list.remove();

        BigInteger remainingElement = list.retrieve();

        assertEquals("Current should point to element after the one removed.", bigInteger3, remainingElement);
    }

    @Test
    public void remove_decreasesSize() {
        ListInterface<BigInteger> list = new List<>();
        list.insert(BigInteger.ONE);

        assertEquals("Initial size is 1.", 1, list.size());
        list.remove();
        assertEquals("Removing an element should decrease size by 1.", 0, list.size());
    }

    @Test
    public void remove_doesNotRemoveBothDuplicates() {
        ListInterface<BigInteger> list = new List<>();
        BigInteger element1 = BigInteger.valueOf(2);
        list.insert(element1);
        list.insert(element1);

        assertEquals("Initial size is 2.", 2, list.size());
        list.remove();
        assertEquals("Removing a duplicated element should decrease size only by 1.", 1, list.size());
    }

    @Test
    public void remove_returnsSameList() {
        ListInterface<BigInteger> list = new List<>();
        list.insert(BigInteger.ONE);

        ListInterface<BigInteger> returnList = list.remove();

        assertSame("Returned list should be the same.", list, returnList);
    }

    @Test
    public void find_isCorrect() {
        ListInterface<BigInteger> list = new List<>();
        BigInteger bigInteger1 = BigInteger.valueOf(123);
        BigInteger bigInteger2 = BigInteger.valueOf(456);

        assertFalse("Find should return false when list is empty.", list.find(bigInteger1));

        list.insert(bigInteger1).insert(bigInteger2);

        assertTrue("bigInteger1 should be found but is not.", list.find(bigInteger1));
        assertTrue("bigInteger2 should be found but is not.", list.find(bigInteger2));

        BigInteger bigInteger3 = BigInteger.valueOf(123);

        assertTrue("Element should be found when given an equal element.", list.find(bigInteger3));
        assertFalse("Non-existent element should not be found.", list.find(BigInteger.TEN));
    }

    @Test
    public void find_pointsToFirstDuplicate() {
        ListInterface<BigInteger> list = new List<>();
        BigInteger bigInteger1 = BigInteger.valueOf(123);
        list.insert(bigInteger1).insert(bigInteger1);

        assertTrue("List should find the element.", list.find(bigInteger1));
        list.goToNext();
        assertNotNull("Current should point to first duplicate element.", list.retrieve());
    }

    @Test
    public void find_currentPointsCorrectlyWhenNotFound() {
        ListInterface<BigInteger> list = new List<>();
        BigInteger bigInteger1 = BigInteger.valueOf(3);
        BigInteger bigInteger2 = BigInteger.valueOf(4);
        list.insert(bigInteger1).insert(bigInteger2);

        BigInteger bigInteger3 = BigInteger.valueOf(5);
        BigInteger bigInteger4 = BigInteger.valueOf(1);

        list.find(bigInteger3);
        assertEquals("Current should point to last element.", bigInteger2, list.retrieve());

        list.find(bigInteger4);
        assertEquals("Current should point to first element.", bigInteger1, list.retrieve());
    }

    @Test
    public void goToFirst_returnsFalseOnEmptyList() {
        ListInterface<BigInteger> list = new List<>();

        assertFalse("Should return false on empty list.", list.goToFirst());
    }

    @Test
    public void goToFirst_pointsToFirstElement() {
        ListInterface<BigInteger> list = new List<>();
        BigInteger bigInteger1 = BigInteger.valueOf(123);
        BigInteger bigInteger2 = BigInteger.valueOf(456);
        list.insert(bigInteger1).insert(bigInteger2);

        assertTrue("Should return true on non-empty list.", list.goToFirst());
        assertEquals("Should point at first element.", bigInteger1, list.retrieve());
    }

    @Test
    public void goToLast_returnsFalseOnEmptyList() {
        ListInterface<BigInteger> list = new List<>();

        assertFalse("Should return false on empty list.", list.goToLast());
    }

    @Test
    public void goToLast_pointsToFirstElement() {
        ListInterface<BigInteger> list = new List<>();
        BigInteger bigInteger1 = BigInteger.valueOf(123);
        BigInteger bigInteger2 = BigInteger.valueOf(456);
        list.insert(bigInteger1).insert(bigInteger2);

        assertTrue("Should return true on non-empty list.", list.goToLast());
        assertEquals("Should point at first element.", bigInteger2, list.retrieve());
    }

    @Test
    public void goToNext_returnsFalseOnEmptyList() {
        ListInterface<BigInteger> list = new List<>();

        assertFalse("Should return false on empty list.", list.goToNext());
    }

    @Test
    public void goToNext_returnsFalseOnLastElement() {
        ListInterface<BigInteger> list = new List<>();
        list.insert(BigInteger.ONE);

        assertFalse("Should return false on last element.", list.goToNext());
    }

    @Test
    public void goToNext_pointsToNextElement() {
        ListInterface<BigInteger> list = new List<>();
        BigInteger bigInteger1 = BigInteger.valueOf(123);
        BigInteger bigInteger2 = BigInteger.valueOf(456);
        list.insert(bigInteger1).insert(bigInteger2);
        list.goToFirst();

        assertTrue("Should return true when next element exists.", list.goToNext());
        assertEquals("Should point at next element.", bigInteger2, list.retrieve());
    }

    @Test
    public void goToPrevious_returnsFalseOnEmptyList() {
        ListInterface<BigInteger> list = new List<>();

        assertFalse("Should return false on empty list.", list.goToPrevious());
    }

    @Test
    public void goToPrevious_returnsFalseOnFirstElement() {
        ListInterface<BigInteger> list = new List<>();
        list.insert(BigInteger.ONE);

        assertFalse("Should return false on empty list.", list.goToPrevious());
    }

    @Test
    public void goToPrevious_pointsToPreviousElement() {
        ListInterface<BigInteger> list = new List<>();
        BigInteger bigInteger1 = BigInteger.valueOf(123);
        BigInteger bigInteger2 = BigInteger.valueOf(456);
        list.insert(bigInteger1).insert(bigInteger2);
        list.goToLast();

        assertTrue("Should return true when previous element exists.", list.goToPrevious());
        assertEquals("Should point at previous element.", bigInteger1, list.retrieve());
    }

}
