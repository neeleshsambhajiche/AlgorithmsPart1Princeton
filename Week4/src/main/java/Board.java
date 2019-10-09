import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @since 04/10/19
 */
public class Board {

    private int[][] tiles;
    private int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        this.tiles = new int[n][n];
        for(int row = 0; row < n; row++) {
            for(int col = 0; col < n; col++) {
                this.tiles[row][col] = tiles[row][col];
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder board = new StringBuilder(String.valueOf(n) + "\n");
        for(int row = 0; row < n; row++) {
            for(int col = 0; col < n; col++) {
                board.append(String.valueOf(tiles[row][col])).append("\t");
            }
            board.append("\n");
        }
        return board.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int hammingDistance = 0;
        for(int row = 0; row < n; row++) {
            for(int col = 0; col < n; col++) {
                if(tiles[row][col] != 0 && tiles[row][col] != (row *  n) + (col + 1)){
                    hammingDistance++;
                }
            }
        }
        return hammingDistance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattanDistance = 0;
        for(int row = 0; row < n; row++) {
            for(int col = 0; col < n; col++) {
                if(tiles[row][col] != 0) {
                    int goalRow = (tiles[row][col] - 1) / n + 1;
                    int goalCol = (tiles[row][col] - 1) % n + 1;
                    int currDist = Math.abs(goalRow - 1 - row) + Math.abs(goalCol -1 - col);
                    manhattanDistance += currDist;
                }
            }
        }
        return manhattanDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if( y == null || y.getClass().getName() != "Board") return false;
        Board b = ((Board) y);
        if(b.n != this.n) return false;
        for(int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (tiles[row][col] != b.tiles[row][col])
                    return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> nBoards = new ArrayList<Board>();
        boolean foundZero = false;
        Board neighbour;
        int[][] copyTiles;
        //Find 0
        for(int row = 0; row < n; row++) {
            for(int col = 0; col < n; col++) {
                if(tiles[row][col] == 0) {
                    foundZero = true;
                    if((row == 0 || row == n -1) && (col == 0 || col == n -1)) {
                        copyTiles = copyTiles(tiles);
                        copyTiles[row][col] = copyTiles[row][Math.abs(col - 1)];
                        copyTiles[row][Math.abs(col - 1)] = 0;
                        neighbour = new Board(copyTiles);
                        nBoards.add(neighbour);

                        copyTiles = copyTiles(tiles);
                        copyTiles[row][col] = copyTiles[Math.abs(row - 1)][col];
                        copyTiles[Math.abs(row - 1)][col] = 0;
                        neighbour = new Board(copyTiles);
                        nBoards.add(neighbour);
                    }
                    else if(row == 0 || row == n -1) {
                        copyTiles = copyTiles(tiles);
                        copyTiles[row][col] = copyTiles[row][col - 1];
                        copyTiles[row][col - 1] = 0;
                        neighbour = new Board(copyTiles);
                        nBoards.add(neighbour);

                        copyTiles = copyTiles(tiles);
                        copyTiles[row][col] = copyTiles[row][col + 1];
                        copyTiles[row][col + 1] = 0;
                        neighbour = new Board(copyTiles);
                        nBoards.add(neighbour);

                        copyTiles = copyTiles(tiles);
                        copyTiles[row][col] = copyTiles[Math.abs(row - 1)][col];
                        copyTiles[Math.abs(row - 1)][col] = 0;
                        neighbour = new Board(copyTiles);
                        nBoards.add(neighbour);
                    }
                    else if(col == 0 || col == n -1) {
                        copyTiles = copyTiles(tiles);
                        copyTiles[row][col] = copyTiles[row - 1][col];
                        copyTiles[row - 1][col] = 0;
                        neighbour = new Board(copyTiles);
                        nBoards.add(neighbour);

                        copyTiles = copyTiles(tiles);
                        copyTiles[row][col] = copyTiles[row + 1][col];
                        copyTiles[row + 1][col] = 0;
                        neighbour = new Board(copyTiles);
                        nBoards.add(neighbour);

                        copyTiles = copyTiles(tiles);
                        copyTiles[row][col] = copyTiles[row][Math.abs(col - 1)];
                        copyTiles[row][Math.abs(col - 1)] = 0;
                        neighbour = new Board(copyTiles);
                        nBoards.add(neighbour);
                    }
                    else {
                        copyTiles = copyTiles(tiles);
                        copyTiles[row][col] = copyTiles[row - 1][col];
                        copyTiles[row - 1][col] = 0;
                        neighbour = new Board(copyTiles);
                        nBoards.add(neighbour);

                        copyTiles = copyTiles(tiles);
                        copyTiles[row][col] = copyTiles[row + 1][col];
                        copyTiles[row + 1][col] = 0;
                        neighbour = new Board(copyTiles);
                        nBoards.add(neighbour);

                        copyTiles = copyTiles(tiles);
                        copyTiles[row][col] = copyTiles[row][col - 1];
                        copyTiles[row][col - 1] = 0;
                        neighbour = new Board(copyTiles);
                        nBoards.add(neighbour);

                        copyTiles = copyTiles(tiles);
                        copyTiles[row][col] = copyTiles[row][col + 1];
                        copyTiles[row][col + 1] = 0;
                        neighbour = new Board(copyTiles);
                        nBoards.add(neighbour);
                    }
                    break;
                }
            }
            if (foundZero) break;
        }

        return nBoards;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if(n == 1) return new Board(tiles);
        int[][] newTiles = copyTiles(this.tiles);
        int row1 = 0;
        int col1 = 0;
        int row2 = 0;
        int col2 = 0;
        boolean found1 = false;
        boolean found2 = false;
        for(int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if(newTiles[row][col] != 0) {
                    if(!found1) {
                        row1 = row;
                        col1 = col;
                        found1 = true;
                    }
                    else {
                        row2 = row;
                        col2 = col;
                        found2 = false;
                        break;
                    }
                }
            }
            if(found2) break;
        }
        newTiles[row1][col1] = tiles[row2][col2];
        newTiles[row2][col2] = tiles[row1][col1];
        return new Board(newTiles);
    }

    private int[][] copyTiles(int[][] tiles) {
        int[][] newTiles = new int[n][n];
        for(int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                newTiles[row][col] = tiles[row][col];
            }
        }
        return newTiles;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        System.out.println(initial.toString());
        System.out.println(initial.twin().toString());
        initial.manhattan();
        Iterable<Board> neighbours = initial.neighbors();
        Iterator<Board> itr = neighbours.iterator();
        while(itr.hasNext()) {
            System.out.println(itr.next().toString());
        }
        initial.hamming();
    }

}
