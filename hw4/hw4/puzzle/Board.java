package hw4.puzzle;


import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState{
    /**Constructs a board from an N-by-N array of tiles where
     tiles[i][j] = tile at row i, column j*/
    private int[][] table;
    private int N;
    private final int BLANK = 0;
    public Board(int[][] tiles){
        this.N = tiles.length;
        this.table = new int[N][N];
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                this.table[i][j] = tiles[i][j];
            }
        }
    }

    /**Returns value of tile at row i, column j (or 0 if blank)*/
    public int tileAt(int i, int j){
        if (i < 0 || i > N-1 || j < 0 || j > N-1){
            throw new IndexOutOfBoundsException("Index is out of bound");
        }
        return table[i][j];
    }

    /**Returns the board size N*/
    public int size(){
        return N;
    }

    /**Hamming estimate described below*/
    public int hamming(){
        int cnt = 0;
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                if (tileAt(i, j) != i * N + j + 1 && tileAt(i, j) != BLANK) {
                    cnt++;
                }
            }
        }
        return cnt;
    }

    /**Manhattan estimate described below*/
    public int manhattan(){
        int cnt = 0;

        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                int tile = tileAt(i, j);
                if (tile != BLANK) {
                    int row = (tile - 1) / N;
                    int col = (tile - 1) % N;
                    cnt += Math.abs(i - row) + Math.abs(j - col);
                }
            }
        }

        return cnt;
    }

    /**Returns true if this board's tile values are the same
     position as y's*/
    public boolean equals(Object y){
        if (this == y) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (this.getClass() != y.getClass()) {
            return false;
        }

        Board that = (Board) y;

        /* can be different sizes */
        if (this.N != that.N) {
            return false;
        }

        for (int i = 0; i < this.N; i += 1) {
            for (int j = 0; j < this.N; j += 1) {
                if (this.tileAt(i, j) != that.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**Estimated distance to goal. This method should
     simply return the results of manhattan() when submitted to
     Gradescope.*/
    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    /**Returns the neighbors of the current board*/
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;

    }

    /** Returns the string representation of the board.
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }


}
