import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @since 15/09/19
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private int N;
    private Item[] s;

    // construct an empty randomized queue
    public RandomizedQueue() {
        s = (Item[]) new Object[1];
        N = 0;
    }

    private void resize(int capacity) {
        Item[] newArray = (Item[]) new Object[capacity];
        for(int i = 0; i < N; i++) {
            newArray[i] = s[i];
        }
        s = newArray;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return N==0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return N;
    }

    // add the item
    public void enqueue(Item item) {
        if(item ==  null) throw new IllegalArgumentException();
        if(N == s.length) resize(s.length * 2);
        s[N++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if(isEmpty()) throw new NoSuchElementException();
        int randIndex = StdRandom.uniform(0, N);
        Item item = s[randIndex];
        s[randIndex] = s[--N];
        if(N > 0 && N == s.length/4) resize(s.length/2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if(isEmpty()) throw new NoSuchElementException();
        int randIndex = StdRandom.uniform(0, N);
        return s[randIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() { return new RandomizedQueueIterator();}

    private class RandomizedQueueIterator implements Iterator<Item> {

        private Item[] newArray;
        int iteratorArrayLength;
        public RandomizedQueueIterator() {
            newArray =  (Item[]) new Object[N];
            for( int i = 0; i < N; i++) {
                newArray[i] = s[i];
            }
            iteratorArrayLength = N;
        }

        public boolean hasNext() { return iteratorArrayLength != 0; }
        public Item next() {
            if(!hasNext()) throw new NoSuchElementException();
            int randIndex = StdRandom.uniform(0, iteratorArrayLength);
            Item item = newArray[randIndex];
            newArray[randIndex] = newArray[--iteratorArrayLength];
            return item;
        }


        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        rq.size();
        rq.enqueue("Hi");
        rq.enqueue("There");
        rq.enqueue("is");
        rq.enqueue("a");
        rq.enqueue("cat");
        System.out.println(rq.size());
        System.out.println(rq.dequeue());
        System.out.println(rq.sample());
        Iterator<String> iterator = rq.iterator();
        for(String str: rq) {
            System.out.println(str);
        }

    }

}
