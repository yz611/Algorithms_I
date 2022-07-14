import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private double[] prediction;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 0 || trials < 0) {
            throw new IllegalArgumentException("both values must be positive");
        }
        int number = n * n;
        prediction = new double[trials];
        for (int tr = 0; tr < trials; tr++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int rd = StdRandom.uniform(1, number);
                int row, col;
                if (rd % n != 0) {
                    row = rd / n + 1;
                    col = rd % n;
                }
                else {
                    row = rd / n;
                    col = n;
                }
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                }
            }
            prediction[tr] = (double) perc.numberOfOpenSites() / number;
        }
    }


    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(prediction);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(prediction);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(prediction.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(prediction.length);
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats percStats = new PercolationStats(Integer.parseInt(args[0]),
                                                          Integer.parseInt(args[1]));
        StdOut.println("mean                    = " + percStats.mean());
        StdOut.println("stddev                  = " + percStats.stddev());
        StdOut.println("95% confidence interval = [" + percStats.confidenceLo() + ", " +
                               percStats.confidenceHi() + "]");
    }

}
