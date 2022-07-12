import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        int counter = 1;
        String champion = "";
        while (!StdIn.isEmpty()) {
            String next = StdIn.readString();
            if (StdRandom.bernoulli((double) 1 / counter)) {
                champion = next;
            }
            counter = counter + 1;
        }
        StdOut.println(champion);
    }
}
