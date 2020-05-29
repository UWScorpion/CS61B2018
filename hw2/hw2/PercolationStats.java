package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    private double[] C;
    private int N;
    /**perform T independent experiments on an N-by-N grid*/
    public PercolationStats(int N, int T, PercolationFactory pf){
        if (N <= 0 || T <= 0){
            throw new IllegalArgumentException("N must be positive");
        }
        C = new double[T];
        this.N = N;
        for (int i = 0; i < T; i++){
            Percolation item = pf.make(N);
            while(!item.percolates()){
                int rmRow = StdRandom.uniform(N);
                int rmCol = StdRandom.uniform(N);
                item.open(rmRow, rmCol);
            }
            C[i] = (double) item.numberOfOpenSites() / (N * N);
        }
    }

    /**sample mean of percolation threshold*/
    public double mean(){
        return StdStats.mean(C);
    }


    /**sample standard deviation of percolation threshold*/
    public double stddev(){
        return StdStats.stddev(C);
    }

    /**low endpoint of 95% confidence interval*/
    public double confidenceLow(){
        return mean() - 1.96 * Math.sqrt(stddev() / C.length);
    }

    /**high endpoint of 95% confidence interval*/
    public double confidenceHigh(){
        return mean() + 1.96 * Math.sqrt(stddev() / C.length);
    }
}
