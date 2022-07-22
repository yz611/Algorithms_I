import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int size;
    private int nOpen;
    private final int number;
    private boolean[][] open;
    private final WeightedQuickUnionUF uf;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        nOpen = 0;
        size = n;
        number = n * n;
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        open = new boolean[n][n];
        uf = new WeightedQuickUnionUF(number + 2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException("row, col indices between 1 and n");
        }
        if (isOpen(row, col))
            return;
        open[row - 1][col - 1] = true;
        nOpen++;
        if (row == 1) {
            uf.union(0, col);
            if (isOpen(2, col))
                uf.union(col, size + col);
            return;
        }


        if (row == size) {
            uf.union(number + 1, number - size + col);
            if (isOpen(size - 1, col))
                uf.union(number - size + col, number - 2 * size + col);
            return;


        }
        if (col == 1) {
            if (isOpen(row, 2))
                uf.union((row - 1) * size + 1, (row - 1) * size + 2);
            if (isOpen(row + 1, 1))
                uf.union((row - 1) * size + 1, row * size + 1);
            if (isOpen(row - 1, 1))
                uf.union((row - 1) * size + 1, (row - 2) * size + 1);
            return;

        }
        if (col == size) {
            if (isOpen(row - 1, size))
                uf.union(row * size, (row - 1) * size);
            if (isOpen(row + 1, size))
                uf.union(row * size, (row + 1) * size);
            if (isOpen(row, size - 1))
                uf.union(row * size, row * size - 1);
            return;
        }

        if (

                isOpen(row, col + 1))
            uf.union((row - 1) * size + col, (row - 1) * size + col + 1);
        if (

                isOpen(row, col - 1))
            uf.union((row - 1) * size + col, (row - 1) * size + col - 1);
        if (

                isOpen(row - 1, col))
            uf.union((row - 1) * size + col, (row - 2) * size + col);
        if (

                isOpen(row + 1, col))
            uf.union((row - 1) * size + col, row * size + col);

    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row > 0 && row <= size && col > 0 && col <= size) {
            return open[row - 1][col - 1];
        }
        throw new IllegalArgumentException("row, col indices between 1 and n");
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row > 0 && row <= size && col > 0 && col <= size) {
            return uf.find(0) == uf.find((row - 1) * size + col);
        }
        throw new IllegalArgumentException("row, col indices between 1 and n");
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return nOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(0) == uf.find(number + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation perc = new Percolation(Integer.parseInt(args[0]));
        StdOut.println(perc.isFull(1, 1));
        StdOut.println(perc.isOpen(1, 1));
        perc.open(1, 1);
        perc.open(1, 3);
        StdOut.println(perc.isFull(1, 1));
        StdOut.println(perc.isOpen(1, 1));
        StdOut.println(perc.isFull(3, 6));
        StdOut.println(perc.isOpen(3, 6));
        perc.open(3, 6);
        StdOut.println(perc.isFull(3, 6));
        StdOut.println(perc.isFull(1, 3));
        StdOut.println(perc.isOpen(3, 6));
        int n = perc.size;
        int number = perc.number;

        for (int tr = 0; tr < Integer.parseInt(args[1]); tr++) {
            int row, col;
            int rd = StdRandom.uniform(1, number + 1);
            if (rd % n != 0) {
                row = rd / n + 1;
                col = rd % n;
            }
            else {
                row = rd / n;
                col = n;
            }
            perc.open(row, col);
        }
        StdOut.println(perc.numberOfOpenSites());
    }
}
