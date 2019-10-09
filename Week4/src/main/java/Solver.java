import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * @since 04/10/19
 */
public class Solver {

    private Board initialBoard;
    private int moves;
    private ArrayList<Board> solutionBList;
    private boolean isSolvableBol;

    private class SearchNode implements Comparable<SearchNode>  {
        Board board;
        int moves;
        SearchNode previous = null;
        int priority;

        public SearchNode(Board board, int moves) {
            this.board = board;
            this.moves = moves;
            priority = board.manhattan() + this.moves;
        }

        public SearchNode(Board board, int moves, SearchNode previous) {
            this(board, moves);
            this.previous = previous;
        }

        public int compareTo(SearchNode that) {
            if(priority < that.priority) return -1;
            if(priority > that.priority) return 1;
            return 0;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if(initial == null) throw new IllegalArgumentException("Argument is null");

        initialBoard = initial;
        SearchNode initialSN = new SearchNode(initial, 0);
        MinPQ<SearchNode> gameTree = new MinPQ<>();
        gameTree.insert(initialSN);

        Board twin = initial.twin();
        SearchNode twinSN = new SearchNode(twin, 0);
        MinPQ<SearchNode> twinGameTree = new MinPQ<>();
        twinGameTree.insert(twinSN);

        boolean initalCompute = true;
        boolean solutionFound = false;
        moves = 0;
        int twinMoves = 0;

        solutionBList = new ArrayList<Board>();

        if(initial.isGoal()) {
            solutionFound = true;
            isSolvableBol = true;
            solutionBList.add(initial);
        }

        while (!solutionFound) {
            if (initalCompute) {
                moves++;
                SearchNode currMin = gameTree.delMin();
                Iterator<Board> currIterator = currMin.board.neighbors().iterator();
                while (currIterator.hasNext()) {
                    Board currBoard = currIterator.next();
                    if (moves == 1 || !currBoard.equals(currMin.previous.board)) {
                        gameTree.insert(new SearchNode(currBoard, moves, currMin));
                    }
                }
                moves = gameTree.min().moves;
                if (gameTree.min().board.isGoal()) {
                    SearchNode node = gameTree.delMin();
                    solutionBList.add(node.board);
                    while(node.previous != null) {
                        node = node.previous;
                        solutionBList.add(node.board);
                    }
                    solutionFound = true;
                    isSolvableBol = true;
                    Collections.reverse(solutionBList);
                }
                initalCompute = false;
            } else {
                twinMoves++;
                SearchNode currMin = twinGameTree.delMin();
                Iterator<Board> currIterator = currMin.board.neighbors().iterator();
                while (currIterator.hasNext()) {
                    Board currBoard = currIterator.next();
                    if (twinMoves == 1 || !currBoard.equals(currMin.previous.board)) {
                        twinGameTree.insert(new SearchNode(currBoard, twinMoves, currMin));
                    }
                }
                twinMoves = twinGameTree.min().moves;
                if (twinGameTree.min().board.isGoal()) {
                    solutionFound = true;
                    isSolvableBol = false;
                    moves = -1;
                    solutionBList = null;
                }
                initalCompute = true;
            }
        }
    }


    // is the initial board solvable? (see below)
    public boolean isSolvable(){
        return isSolvableBol;
    }


    // min number of moves to solve initial board
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        return solutionBList;
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);


/*        int n = 3;
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = i * n + j;

        Board initial = new Board(tiles);*/

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
