package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int N;
    private boolean[][] space;
    private int OpenSitesNum;
    WeightedQuickUnionUF uf;
    //virtual top line
    private int top;
    //virtual bottom line
    private int bottom;

    /**create N-by-N grid, with all sites initially blocked*/
    public Percolation(int N){
        // edge case N<=0
        if (N <= 0){
            throw new IllegalArgumentException("N must be positive");
        }
        this.N = N;
        space = new boolean[N][N];
        uf = new WeightedQuickUnionUF(N * N + 2);
        OpenSitesNum = 0;
        top = N * N;
        bottom = N * N + 1;
    }
    /**helper function to convert (row, col) to id*/
    private int rowcolToID(int row, int col){
        int num = row * N + col;
        return num;
    }

    /** check if row or col is valid or not*/
    private boolean check(int row, int col){
        if (row < 0 || row >= N || col <0 || col >= N){
            return false;
        }
        return true;
    }

    /**open the site (row, col) if it is not open already*/
    public void open(int row, int col){
        if (!check(row, col)){
            throw new IndexOutOfBoundsException("row or col is wrong");
        }
        if (space[row][col] == false) {
            space[row][col] = true;
            OpenSitesNum++;
            Link(row, col);
        }

    }
    /** this function check the all four directions of the current position
     * link them if it is open and valid*/
    private void Link(int row, int col){
        int curr = rowcolToID(row, col);
        if (row == 0){
            uf.union(top, curr);
        }
        if (row == N-1){
            uf.union(bottom, curr);
        }
        //up
        if (check(row-1, col) && isOpen(row-1, col)){
            int up = rowcolToID(row-1, col);
            uf.union(curr, up);
        }
        //Down
        if (check(row+1, col) && isOpen(row+1, col)){
            int down = rowcolToID(row+1, col);
            uf.union(curr, down);
        }
        //Left
        if (check(row, col-1) && isOpen(row, col-1)){
            int left = rowcolToID(row, col-1);
            uf.union(curr, left);
        }
        //Right
        if (check(row, col+1) && isOpen(row, col+1)){
            int right = rowcolToID(row, col+1);
            uf.union(curr, right);
        }
    }


    /**is the site (row, col) open?*/
    public boolean isOpen(int row, int col){
        if (!check(row, col)){
            throw new IndexOutOfBoundsException("row or col is wrong");
        }
        return space[row][col];
    }

    /**is the site (row, col) full?*/
    public boolean isFull(int row, int col){
        if (!check(row, col)){
            throw new IndexOutOfBoundsException("row or col is wrong");
        }
        int curr = rowcolToID(row, col);
        if (uf.connected(top, curr)){
            return true;
        }
        return false;
    }

    /**number of open sites, constant time*/
    public int numberOfOpenSites(){
        return OpenSitesNum;
    }

    /**does the system percolate?*/
    public boolean percolates(){
        return uf.connected(top, bottom);
    }

    /**use for unit testing (not required)*/
    public static void main(String[] args){

    }
}
