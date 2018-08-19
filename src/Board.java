import edu.princeton.cs.algs4.Queue;

public class Board {
    
    private final int[][] blocks;
    
    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks)
    {
        if (blocks == null)
            throw new java.lang.IllegalArgumentException();
        
        this.blocks = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++)
            for (int j = 0; j < blocks.length; j++)
                this.blocks[i][j] = blocks[i][j];
    }
     
    // board dimension n
    public int dimension()
    {
        return blocks.length;
    }
    
    // number of blocks out of place
    public int hamming() 
    {
        int numberOfBlocksOutOfPlace = 0;
        for (int i = 0; i < blocks.length; i++)
            for (int j = 0; j < blocks.length; j++)
            {
                if (i == blocks.length - 1 && j == blocks.length - 1)
                {
                    if (blocks[i][j] != 0)
                        numberOfBlocksOutOfPlace++;
                    continue;
                }
                
                if (blocks[i][j] != i * blocks.length + j + 1 && blocks[i][j] != 0)
                    numberOfBlocksOutOfPlace++;
            }
        
        return numberOfBlocksOutOfPlace;
    }
    
    // sum of Manhattan distances between blocks and goal
    public int manhattan()
    {
        int distancesSum = 0;
        for (int i = 0; i < blocks.length; i++)
            for (int j = 0; j < blocks.length; j++)
            {
                int currentValue = blocks[i][j];
                
                if (currentValue == 0)
                    continue;
                
                int expectedI = (currentValue - 1) / blocks.length;
                int expectedJ = (currentValue - 1) % blocks.length;
                
                if (i == expectedI && j == expectedJ)
                    continue;
                
                if (i != expectedI)
                    distancesSum += Math.abs(expectedI - i);
                
                if (j != expectedJ)
                    distancesSum += Math.abs(expectedJ - j);
            }
        
        return distancesSum;
    }
    
    // is this board the goal board?
    public boolean isGoal()
    {
       for (int i = 0; i < blocks.length; i++)
           for (int j = 0; j < blocks.length; j++)
           {
               if (i == blocks.length - 1 && j == blocks.length - 1)
               {
                   if (blocks[i][j] != 0)
                       return false;
               }
               else
               if (blocks[i][j] != i * blocks.length + j + 1)
                   return false;
           }
       
       return true;   
    }
    
    // a board that is obtained by exchanging any pair of blocks
    public Board twin()
    {
        int[][] twinBlocks = copyOf(blocks);
        
        if (blocks[0][0] != 0 && blocks[0][1] != 0)
        {
            twinBlocks[0][0] = blocks[0][1];
            twinBlocks[0][1] = blocks[0][0];
        }
        else
        {
            twinBlocks[1][0] = blocks[1][1];
            twinBlocks[1][1] = blocks[1][0];
        }
        
        return new Board(twinBlocks);
    }
    
    // does this board equal y?
    public boolean equals(Object y)
    {
        if (y == this)
            return true;
        
        if (y == null)
            return false;
        
        if (y.getClass() != this.getClass())
            return false;
        
        Board anotherBoard = (Board) y;
        
        if (this.dimension() != anotherBoard.dimension())
            return false;
        
        for (int i = 0; i < this.blocks.length; i++)
            for (int j = 0; j < this.blocks.length; j++)
                if (this.blocks[i][j] != anotherBoard.blocks[i][j])
                    return false;
        
        return true;
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors()
    {
        int n = blocks.length;
        
        int iEmpty = -1;
        int jEmpty = -1;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
            {
                if (blocks[i][j] == 0)
                {
                    iEmpty = i;
                    jEmpty = j;
                }
            }
        
        Queue<Board> neighbors = new Queue<Board>();
        if (iEmpty < n - 1)
            enqueueNeighbor(iEmpty, jEmpty, iEmpty + 1, jEmpty, neighbors);
        
        if (iEmpty > 0)
            enqueueNeighbor(iEmpty, jEmpty, iEmpty - 1, jEmpty, neighbors);  
            
        if (jEmpty < n - 1)
            enqueueNeighbor(iEmpty, jEmpty, iEmpty, jEmpty + 1, neighbors);

        if (jEmpty > 0)
            enqueueNeighbor(iEmpty, jEmpty, iEmpty, jEmpty - 1, neighbors);
        
        return neighbors;
    }
    
    private void enqueueNeighbor(int iEmpty, int jEmpty, 
            int iToMove, int jToMove, Queue<Board> neighborsQueue)
    {
        int[][] neighborBlocks = copyOf(blocks);
        moveToEmptySpace(iEmpty, jEmpty, iToMove, jToMove, neighborBlocks);
        neighborsQueue.enqueue(new Board(neighborBlocks));
    }
    
    private void moveToEmptySpace(int iEmpty, int jEmpty, 
            int iToMove, int jToMove, int[][] neighborsBlocks)
    {
        neighborsBlocks[iEmpty][jEmpty] = neighborsBlocks[iToMove][jToMove];
        neighborsBlocks[iToMove][jToMove] = 0;
    }
    
    private int[][] copyOf(int[][] oldArray)
    {
        int[][] newArray = new int[oldArray.length][oldArray.length];
        
        for (int i = 0; i < oldArray.length; i++)
            for (int j = 0; j < oldArray[i].length; j++)
                newArray[i][j] = oldArray[i][j];
        
        return newArray;
    }
    
    // string representation of this board (in the output format specified below)
    public String toString()
    {
        int n = blocks.length;
        
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    
    // unit tests (not graded)
    public static void main(String[] args)
    {
        // only because api neeeds it
    }
}