import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] open;
    private int[][] ind;
    private WeightedQuickUnionUF uf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        open = new int[n][n];
        ind = new int[n][n];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                ind[row][col] = row * n + col;
            }
        }
        uf = new WeightedQuickUnionUF(n * n + 2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int n = open.length;
        if (!(row > 0 && row <= open.length && col > 0 && col <= open.length)) {
            throw new IllegalArgumentException("row, col indices between 1 and n");
        }
        open[row - 1][col - 1] = 1;
        if (row == 1) {
            if (col == 1) {
                if (isOpen(1, 2))
                    uf.union(ind[0][0], ind[0][1]);
                if (isOpen(2, 1))
                    uf.union(ind[0][0], ind[1][0]);
                return;
            }
            if (col == n) {
                if (isOpen(1, n - 1))
                    uf.union(ind[0][n - 1], ind[0][n - 2]);
                if (isOpen(2, n))
                    uf.union(ind[0][n - 1], ind[1][n - 1]);
                return;
            }
            else {
                if (isOpen(1, col - 1))
                    uf.union(ind[0][col - 1], ind[0][col - 2]);
                if (isOpen(1, col + 1))
                    uf.union(ind[0][col - 1], ind[0][col]);
                if (isOpen(2, col))
                    uf.union(ind[0][col - 1], ind[1][col - 1]);
                return;
            }
        }

        if (row == n) {
            if (col == 1) {
                if (isOpen(n, 2))
                    uf.union(ind[n - 1][0], ind[n - 1][1]);
                if (isOpen(n - 1, 1))
                    uf.union(ind[n - 1][0], ind[n - 2][0]);
                return;
            }

            if (col == n) {
                if (isOpen(n, n - 1))
                    uf.union(ind[n - 1][n - 1], ind[n - 1][n - 2]);
                if (isOpen(n - 1, n))
                    uf.union(ind[n - 1][n - 1], ind[n - 2][n - 1]);
                return;
            }
            else {
                if (isOpen(n, col - 1))
                    uf.union(ind[n - 1][col - 1], ind[n - 1][col - 2]);
                if (isOpen(n, col + 1))
                    uf.union(ind[n - 1][col - 1], ind[n - 1][col]);
                if (isOpen(n - 1, col))
                    uf.union(ind[n - 1][col - 1], ind[n - 2][col - 1]);
                return;

            }
        }
        if (col == 1) {
            if (isOpen(row, 2))
                uf.union(ind[row - 1][0], ind[row - 1][1]);
            if (isOpen(row + 1, 1))
                uf.union(ind[row - 1][0], ind[row][0]);
            if (isOpen(row - 1, 1))
                uf.union(ind[row - 1][0], ind[row - 2][0]);
            return;

        }
        if (col == n) {
            if (isOpen(row - 1, n))
                uf.union(ind[row - 1][n - 1], ind[row - 2][n - 1]);
            if (isOpen(row + 1, n))
                uf.union(ind[row - 1][n - 1], ind[row][n - 1]);
            if (isOpen(row, n - 1))
                uf.union(ind[row - 1][n - 1], ind[row - 1][n - 2]);
            return;
        }

        if (isOpen(row, col + 1))
            uf.union(ind[row - 1][col - 1], ind[row - 1][col]);
        if (isOpen(row, col - 1))
            uf.union(ind[row - 1][col - 1], ind[row - 1][col - 2]);
        if (isOpen(row - 1, col))
            uf.union(ind[row - 1][col - 1], ind[row - 2][col - 1]);
        if (isOpen(row + 1, col))
            uf.union(ind[row - 1][col - 1], ind[row][col - 1]);

    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!(row > 0 && row <= open.length && col > 0 && col <= open.length)) {
            throw new IllegalArgumentException("row, col indices between 1 and n");
        }
        return open[row - 1][col - 1] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!(row > 0 && row <= open.length && col > 0 && col <= open.length)) {
            throw new IllegalArgumentException("row, col indices between 1 and n");
        }
        for (int count = 0; count <= open.length; count++) {
            if (uf.find(ind[0][count]) == uf.find(ind[row - 1][col - 1])) {
                return true;
            }
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int count = 0;
        int n = open.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                count += open[i][j];
            }
        }
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        int n = open.length;
        int upperVirtualNode = n * n;
        int lowerVirtualNode = n * n + 1;

        for (
                int i = 0;
                i <= n - 1; i++) {
            if (isOpen(1, i + 1)) {
                uf.union(ind[0][i], upperVirtualNode);
            }
            if (isOpen(n, i + 1)) {
                uf.union(ind[n - 1][i], lowerVirtualNode);
            }
        }
        return uf.find(upperVirtualNode) == uf.find(lowerVirtualNode);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
