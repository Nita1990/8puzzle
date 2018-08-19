import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    
    private final Stack<Board> solutionBoards;
    private final boolean isSolvable;
    private final int numberOfMoves;
    
    private class SearchNode implements Comparable<SearchNode>
    {
        private Board currentBoard;
        private int numberOfMoves = -1;
        private SearchNode previousNode;
        
        private int manhattan;
        
        public SearchNode(Board currentBoard, 
                int numberOfMoves, 
                SearchNode previousNode)
        {
            this.currentBoard = currentBoard;
            this.numberOfMoves = numberOfMoves;
            this.previousNode = previousNode;
            this.manhattan = currentBoard.manhattan();
        }

        @Override
        public int compareTo(SearchNode anotherNode) {
            int currentPriority = this.manhattan + this.numberOfMoves;
            int anotherPriority = anotherNode.manhattan + 
                    anotherNode.numberOfMoves;
            
            if (currentPriority > anotherPriority)
                return 1;
            
            if (currentPriority < anotherPriority)
                return -1;
            
            return 0;
        }
    }
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial)
    {
        if (initial == null)
            throw new java.lang.IllegalArgumentException();
        
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        MinPQ<SearchNode> twinPq = new MinPQ<SearchNode>();
        
        pq.insert(new SearchNode(initial, 0, null));
        twinPq.insert(new SearchNode(initial.twin(), 0, null));
        
        SearchNode goalNode = null;
        solutionBoards = new Stack<Board>();
        
        while (true)
        {
           goalNode = processNodeAndReturnIfGoal(pq);
           if (goalNode != null)
           {
               isSolvable = true;
               numberOfMoves = goalNode.numberOfMoves;
               
               SearchNode currentNode = goalNode;
               
               while (currentNode != null)
               {
                   solutionBoards.push(currentNode.currentBoard);
                   currentNode = currentNode.previousNode;
               }
               break;
           }
           
           if (processNodeAndReturnIfGoal(twinPq) != null)
           {
               isSolvable = false;
               numberOfMoves = -1;
               break;
           }
        }
    }
    
    private SearchNode processNodeAndReturnIfGoal(MinPQ<SearchNode> pq)
    {
        SearchNode currentNode = pq.delMin();
        
        if (currentNode.currentBoard.isGoal())
            return currentNode;
        
        Iterable<Board> neighbors = currentNode.currentBoard.neighbors();
        
        for (Board neighbor: neighbors)
        {
            if (currentNode.previousNode == null ||
                    !neighbor.equals(currentNode.previousNode.currentBoard))
                pq.insert(new SearchNode(neighbor, currentNode.numberOfMoves + 1, currentNode));
        }
        
        return null;
    }
    
    // is the initial board solvable?
    public boolean isSolvable()
    {
        return isSolvable;
    }
    
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves()
    {
        if (!isSolvable)
            return -1;
        
        return numberOfMoves;
    }
    
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution()
    {
        if (!isSolvable)
            return null;

        return solutionBoards;
    }
    
    // solve a slider puzzle 
    public static void main(String[] args)
    {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

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