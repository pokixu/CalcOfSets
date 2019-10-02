public class List<E extends Comparable> implements ListInterface<E>{

    private class Node {

        private E data;
        private Node prior,
                next;

        public Node(E data) {
            this(data, null, null);
        }

        public Node(E data, Node prior, Node next) {
            this.data = data == null ? null : data;
            this.prior = prior;
            this.next = next;
        }

    }

    private Node head,
            current;

    public List() {
        head = null;
        current = head;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public ListInterface<E> init() {
        head = null;
        current = head;
        return this;
    }

    @Override
    public int size() {
        int count = 0;
        Node temp = head;
        while(temp != null){
            count++;
            temp = temp.next;
        }
        return count;
    }

    @Override
    public ListInterface<E> insert(E d) {
        if(head == null){
            head = new Node(d);
            current = head;
        }
        else {
            current = head;
            if (current.data.compareTo(d) > 0) {
                this.insertAtBeginning(d);
            }
            else {
                while (current.next != null) {
                    if (current.next.data.compareTo(d) > 0) {
                        this.insertInBetween(d);
                        return this;
                    }
                    current = current.next;
                }
                this.insertAtEnd(d);
            }
        }
        return this;
    }

    private void insertAtBeginning(E d) {
        Node toInsert = new Node(d, null, current);
        current.prior = toInsert;
        head = toInsert;
        current = head;
    }

    private void insertInBetween(E d) {
        Node toInsert = new Node(d, current, current.next);
        current.next = toInsert;
        toInsert.next.prior = toInsert;
        current = toInsert;
    }

    private void insertAtEnd(E d) {
        Node toInsert = new Node(d, current, null);
        current.next = toInsert;
        current = toInsert;
    }

    @Override
    public E retrieve() {
        if(head == null){
            return null;
        }
        return current.data;
    }

    @Override
    public ListInterface<E> remove() {
        if (head == null) {
            return null;
        }

        if (current == head && head.next == null) {
            head = null;
        }
        else {
            if (current.next == null){
                current = current.prior;
                current.next = null;
            }
            else{
                current.prior.next = current.next;
                current.next.prior = current.prior;
                current = current.next;
            }
        }
        return this;
    }

    @Override
    public boolean find(E d) {
        if (head == null) {
            current = head;
            return false;
        }

        Node temp = head;
        while(temp != null){
            if(temp.data.equals(d)){
                current = temp;
                return true;
            }
            temp = temp.next;
        }

        if(head.data.compareTo(d) == 1){
            current = head;
        }
        else {
            temp = head;
            while (temp != null) {
                if(temp.data.compareTo(d) == -1){
                    current = temp;
                }
                temp = temp.next;
            }
        }
        return false;
    }

    @Override
    public boolean goToFirst() {
        if(head != null){
            current = head;
            return true;
        }
        return false;
    }

    @Override
    public boolean goToLast() {
        if(head != null) {
            Node temp = head;
            while(temp.next != null){
                temp = temp.next;
            }
            current = temp;
            return true;
        }
        return false;
    }

    @Override
    public boolean goToNext() {
        if(head != null && current.next != null) {
            current = current.next;
            return true;
        }
        return false;
    }

    @Override
    public boolean goToPrevious() {
        if(head != null && current != head) {
            current = current.prior;
            return true;
        }
        return false;
    }

    @Override
    public ListInterface<E> copy() {
        ListInterface<E> twin = new List<>();
        Node temp = head;
        while(temp != null){
            twin.insert(temp.data);
            temp = temp.next;
        }

        return twin;
    }

}
