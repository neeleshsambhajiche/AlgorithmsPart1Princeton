import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @since 15/09/19
 */
public class Deque<Item> implements Iterable<Item> {

    private Node first = null;
    private Node last = null;
    private int length;

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    // construct an empty deque
    public Deque()  {
        length = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return length;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if(item == null) throw new IllegalArgumentException();
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.previous = null;
        if(oldFirst != null ) oldFirst.previous = first;
        if(last == null) last = first;
        length++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if(item == null) throw new IllegalArgumentException();
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.previous = oldLast;
        if(oldLast != null) oldLast.next = last;
        if(first == null) first = last;
        length++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if(isEmpty()) throw new NoSuchElementException();
        Item item = first.item;
        first = first.next;
        if(first != null)
            first.previous = null;
        else last = null;
        length--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if(isEmpty()) throw new NoSuchElementException();
        Item item = last.item;
        last = last.previous;
        if(last != null)
            last.next = null;
        else first = last;
        length--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() { return new DequeIterator(); }

    private class DequeIterator implements Iterator<Item> {

        private Node current = first;

        public boolean hasNext() { return current != null; }
        public Item next() {
            if(current == null) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> dequeOfStrings = new Deque<String>();
        System.out.println(dequeOfStrings.isEmpty());
        dequeOfStrings.addFirst("Hi");
        dequeOfStrings.addLast("There");
        dequeOfStrings.addFirst("1");
        dequeOfStrings.addLast("2");
        System.out.println(dequeOfStrings.size());
        Iterator<String> iterator = dequeOfStrings.iterator();
        for (String str: dequeOfStrings)
            System.out.println(str);

        System.out.println(dequeOfStrings.removeFirst());
        System.out.println(dequeOfStrings.removeLast());
        System.out.println(dequeOfStrings.removeFirst());
        System.out.println(dequeOfStrings.removeFirst());

    }
}
