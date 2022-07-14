import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private double[] prediction;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        int number = n * n;
        prediction = new double[trials];
        for (int tr = 0; tr < trials; tr++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int rd = StdRandom.uniform(number - 1);
                int row = rd / n + 1;
                int col = rd % n + 1;
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
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats percStats = new PercolationStats(n, t);

        StdOut.println("mean                    = " + percStats.mean());
        StdOut.println("stddev                  = " + percStats.stddev());
        StdOut.println("95% confidence interval = [" + percStats.confidenceLo() + ", " +
                               percStats.confidenceHi() + "]");
    }

}
