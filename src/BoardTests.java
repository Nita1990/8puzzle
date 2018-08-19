import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BoardTests {

    @Test
    void isGoalTest_true() {
        
        int[][] blocks = CreateGoalBlocks();
        
        Board board = new Board(blocks);
        assertTrue(board.isGoal());
    }
    
    @Test
    void isGoalTest_false() {
        
        int[][] blocks = CreateGoalBlocks();
        blocks[0][0] = 2;
        blocks[0][1] = 1;
        Board board = new Board(blocks);
        assertFalse(board.isGoal());
    }
    
    @Test
    void twinTest() {
        
        int[][] blocks = CreateGoalBlocks();
        Board board = new Board(blocks);
        
        int[][] expectedTwinBlocks = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++)
            for (int j = 0; j < blocks.length; j++)
                    expectedTwinBlocks[i][j] = blocks[i][j];
        
        expectedTwinBlocks[0][1] = blocks[0][0];
        expectedTwinBlocks[0][0] = blocks[0][1];
        
        Board expectedTwinBoard = new Board(expectedTwinBlocks);
        Board actualTwin = board.twin();
        assertEquals(expectedTwinBoard.toString(), actualTwin.toString());
    }
    
    @Test
    void neighbors2Test() {
        
        int[][] blocks = CreateGoalBlocks();
        Board board = new Board(blocks);
        
        int neighborsCount = 0;
        for (Board neighbor : board.neighbors())
            neighborsCount++;
        
        assertEquals(2, neighborsCount);
    }
    
    @Test
    void neighbors4Test() {
        
        int[][] blocks = CreateGoalBlocks();
        blocks[1][1] = 0;
        blocks[2][2] = 5;
        Board board = new Board(blocks);
        
        int neighborsCount = 0;
        for (Board neighbor : board.neighbors())
            neighborsCount++;
        
        assertEquals(4, neighborsCount);
    }
    
    @Test
    void hammingTest_0() {
        
        int[][] blocks = CreateGoalBlocks();
        Board board = new Board(blocks);
        
        assertEquals(0, board.hamming());
    }
   
    @Test
    void hammingTest_1() {
        
        int[][] blocks = CreateGoalBlocks();
        blocks[2][1] = 0;
        blocks[2][2] = 8;
        Board board = new Board(blocks);
        assertEquals(1, board.hamming());
    }
    
    @Test
    void manhattanTest_0() {
        
        int[][] blocks = CreateGoalBlocks();
        Board board = new Board(blocks);
        
        assertEquals(0, board.manhattan());
    }
    
    @Test
    void manhattanTest_1() {
        
        int[][] blocks = CreateGoalBlocks();
        blocks[2][1] = 0;
        blocks[2][2] = 8;
        Board board = new Board(blocks);
        assertEquals(1, board.manhattan());
    }
      
    
    public static int[][] CreateGoalBlocks()
    {
        int[][] blocks = new int[3][3];

        for (int i = 0; i < blocks.length; i++)
            for (int j = 0; j < blocks.length; j++)
            {
                if (i == blocks.length - 1 && j == blocks.length - 1)
                    blocks[i][j] = 0;
                else
                    blocks[i][j] = i * blocks.length + j + 1;
            }
        
        return blocks;
    }

}
