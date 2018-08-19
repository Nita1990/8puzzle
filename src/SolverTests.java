import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SolverTests {

    @Test
    void OneStepTest() {
        int[][] blocks = BoardTests.CreateGoalBlocks();
        
        blocks[2][2] = 8;
        blocks[2][1] = 0;
        
        Solver solver = new Solver(new Board(blocks));
        Iterable<Board> solution = solver.solution();
        
        int numberOfBoards = 0;
        for (Board board: solution)
            numberOfBoards++;
        assertEquals(2, numberOfBoards);
    }
    
    @Test
    void UnsolvableTest() {
        int[][] blocks = BoardTests.CreateGoalBlocks();
        
        blocks[0][0] = 2;
        blocks[0][1] = 1;
        
        Solver solver = new Solver(new Board(blocks));
        assertFalse(solver.isSolvable());
    }

}
