package hw4.puzzle;
import  edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.*;

public class Solver {

    /** define a search node of the puzzle to be:
     a WorldState.
     the number of moves made to reach this world state from the initial state.
     a reference to the previous search node.
     */
    private class Node{
        public WorldState WS;
        public int num_moves;
        public Node prev;
        public int priority;
        public Node(WorldState W, int n, Node p){
            this.WS = W;
            this.num_moves = n;
            this.prev = p;
            this.priority = num_moves + this.WS.estimatedDistanceToGoal();
        }
    }
    private MinPQ<Node> PQ;
    private Stack<WorldState> solutions;
    private int total;
    /**Constructor which solves the puzzle, computing
     everything necessary for moves() and solution() to
     not have to solve the problem again. Solves the
     puzzle using the A* algorithm. Assumes a solution exists.*/
    public Solver(WorldState initial){
        PQ = new MinPQ<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node a, Node b) {
                return (a.priority -b.priority);
            }
        });
        total = 0;
        PQ.insert(new Node(initial, 0, null));
        total++;
        solutions = new Stack<>();
        Node goal = null;
        while (!PQ.isEmpty()) {
            Node curr = PQ.delMin();
            if (curr.WS.isGoal()) {
                goal = curr;
                break;
            }

            for (WorldState rest : curr.WS.neighbors()) {
                if(curr.prev == null ||!rest.equals(curr.prev.WS)) {
                    PQ.insert(new Node(rest, curr.num_moves + 1, curr));
                    total++;
                }
            }

        }
        while(goal != null){
            solutions.push(goal.WS);
            goal = goal.prev;
        }



    }

    /**return the number of total things ever enqueued in the MinPQ*/
    public int nums(){
        return total;
    }


    /**Returns the minimum number of moves to solve the puzzle starting
     at the initial WorldState.*/
    public int moves(){
        return solutions.size() - 1;
    }

    /**Returns a sequence of WorldStates from the initial WorldState
     to the solution*/
    public Iterable<WorldState> solution(){
        return solutions;
    }
}
