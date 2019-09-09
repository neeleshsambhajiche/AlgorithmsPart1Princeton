import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
/**
 * @since 07/09/19
 */
public class Percolation {

    private boolean[] openSites;
    final private WeightedQuickUnionUF connectedSites;
    final private WeightedQuickUnionUF fullSites;
    final private int length;
    private int numberOfOpenSites=0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if(n <= 0 ) throw new IllegalArgumentException("n should be greater than 0");
        length = n;
        openSites = new boolean[n*n + 2];
        for(int i = 0; i < n*n + 2; i++ ) {
            openSites[i] = false;
        }
        connectedSites = new WeightedQuickUnionUF(n*n + 2);
        fullSites = new WeightedQuickUnionUF(n*n + 1);

        //Open top and bottom sites
        openSites[0] = true;
        openSites[n * n + 1] = true;

    }

    private void validate(int row, int col) {
        if( row < 1 || row > length || col < 1 || col > length )
            throw new IllegalArgumentException("Row and column should be between 1 and " + length);
    }

    private int index(int row, int col) {
        return (row-1) * length + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        int currentIndex = (row-1) * length + col;
        if(!openSites[currentIndex]) {
            openSites[currentIndex] = true;
            numberOfOpenSites++;
            if(row > 1 && isOpen(row - 1, col)) {
                connectedSites.union(currentIndex,index(row - 1, col));
                fullSites.union(currentIndex,index(row - 1, col));
            }

            if(row < length && isOpen(row + 1, col)) {
                connectedSites.union(currentIndex,index(row + 1, col));
                fullSites.union(currentIndex,index(row + 1, col));
            }

            if(col > 1 && isOpen(row, col - 1)) {
                connectedSites.union(currentIndex,index(row, col - 1));
                fullSites.union(currentIndex,index(row, col - 1));
            }

            if(col < length && isOpen(row, col + 1)) {
                connectedSites.union(currentIndex,index(row, col + 1));
                fullSites.union(currentIndex,index(row, col + 1));
            }
            if(row == 1) {
                connectedSites.union(0,currentIndex);
                fullSites.union(0,currentIndex);
            }
            if(row == length) connectedSites.union(currentIndex,length*length + 1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return openSites[index(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return (fullSites.connected(0, index(row,col)));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return connectedSites.connected(0, length * length + 1);
    }
}
