import java.math.BigInteger;

public class Set implements SetInterface {
    /**
     * @param list - each Node object holds a BigInteger number from the set
     */
    private List<BigInteger> list;

    Set() {
        list = new List<>();
        list.init();
    }


    @Override
    public void insert(BigInteger number) throws APException {
        checkIfNull(this);
        if (!list.find(number)) {
            list.insert(number);
        }
    }

    @Override
    public int size() throws APException {
        checkIfNull(this);
        return list.size();
    }

    @Override
    public Set union(Set otherSet) throws APException {
        checkIfNull(this);
        checkIfNull(otherSet);
        if (checkIfEmpty(this)) {
            return otherSet;
        }
        if (checkIfEmpty(otherSet)) {
            return this;
        }

        Set unionSet = new Set();
        list.goToFirst();
        otherSet.list.goToFirst();
        do {
            unionSet.insert(list.retrieve());
        }
        while (list.goToNext());
        do {
            if (!unionSet.list.find(otherSet.list.retrieve())) {
                unionSet.insert(otherSet.list.retrieve());
            }
        }
        while (otherSet.list.goToNext());
        return unionSet;
    }

    @Override
    public Set intersection(Set otherSet) throws APException {
        checkIfNull(this);
        checkIfNull(otherSet);
        Set intersectionSet = new Set();
        if (!(checkIfEmpty(otherSet) || checkIfEmpty(this))) {
            list.goToFirst();
            do {
                if (otherSet.list.find(list.retrieve())) {
                    intersectionSet.insert(list.retrieve());
                }
            }
            while (list.goToNext());
        }
        return intersectionSet;
    }

    @Override
    public Set complement(Set otherSet) throws APException {
        checkIfNull(this);
        checkIfNull(otherSet);
        if (checkIfEmpty(otherSet) || checkIfEmpty(this)) {
            return this;
        }
        Set complement = new Set();
        list.goToFirst();
        do {
            if(!otherSet.list.find(list.retrieve())) {
                complement.list.insert(list.retrieve());
            }
        }
        while (list.goToNext());
        return complement;
    }

    @Override
    public Set symmetric_difference(Set otherSet) throws APException {
        checkIfNull(this);
        checkIfNull(otherSet);
        if (checkIfEmpty(this)) {
            return otherSet;
        }
        if (checkIfEmpty(otherSet)) {
            return this;
        }

        Set symmetric_difference = new Set();
        Set union = this.union(otherSet);
        union.list.goToFirst();
        Set intersection = this.intersection(otherSet);
        do {
            if (!intersection.list.find(union.list.retrieve())) {
                symmetric_difference.insert(union.list.retrieve());
            }
        }
        while (union.list.goToNext());
        return symmetric_difference;
    }

    public String toString(){
        if (list.isEmpty()) {
            return "";
        }
        StringBuffer setStr = new StringBuffer("");
        list.goToFirst();
        do {
            setStr.append(list.retrieve() + " ");
        }
        while (list.goToNext());
        return setStr.toString().substring(0, setStr.toString().length() - 1);
    }

    private void checkIfNull(Set set) throws APException {
        if (set == null) {
            throw new APException("set has not been initialized");
        }
    }

    private boolean checkIfEmpty(Set set) {
        return set.list.isEmpty();
    }
}
