import edu.princeton.cs.algs4.StdIn;

/**
 * @since 15/09/19
 */
public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> rq= new RandomizedQueue<String>();
        while(!StdIn.isEmpty()) {
            String s = StdIn.readString();
            rq.enqueue(s);
        }

        for(int i = 0; i < Integer.parseInt(args[0]); i++) {
            System.out.println(rq.dequeue());
        }

    }
}
