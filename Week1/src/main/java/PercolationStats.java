import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
/**
 * @since 07/09/19
 */
public class PercolationStats {

    final private double[] results;
    private double mean;
    private double standardDeviation;
    private int numOftrails;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if(n<= 0 || trials <= 0) throw new IllegalArgumentException("n and trails should be greater than 0");
        numOftrails = trials;
        results = new double[trials];
        for(int i = 0; i < trials; i++) {
            Percolation grid = new Percolation(n);
            while(!grid.percolates()) {
                int row = StdRandom.uniform(1,n+1);
                int col = StdRandom.uniform(1,n+1);
                grid.open(row,col);
            }
            results[i]= (double) grid.numberOfOpenSites() /(n*n);
        }
        mean = StdStats.mean(results);
        standardDeviation = StdStats.stddev(results);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return standardDeviation;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - 1.96 * standardDeviation / Math.sqrt(numOftrails);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + 1.96 * standardDeviation / Math.sqrt(numOftrails);

    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}
