import greenfoot.*;
import greenfoot.Color;

public class myworld extends World
{
    private Cell[][] cells = new Cell[9][9];
    private Cell selectedCell = null;
    private boolean noteMode = false;


    // Simple sample Sudoku puzzle (0 means empty)
    private int[][] puzzle = {
        {8, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 3, 6, 0, 0, 0, 0, 0},
        {0, 7, 0, 0, 9, 0, 2, 0, 0},
        {0, 5, 0, 0, 0, 7, 0, 0, 0},
        {0, 0, 0, 0, 4, 5, 7, 0, 0},
        {0, 0, 0, 1, 0, 0, 0, 3, 0},
        {0, 0, 1, 0, 0, 0, 0, 6, 8},
        {0, 0, 8, 5, 0, 0, 0, 1, 0},
        {0, 9, 0, 0, 0, 0, 4, 0, 0}
    };

    public myworld()
    {    
        super(450, 450, 1);
        drawGrid();
        createCells();
    }

    private void drawGrid()
    {
        GreenfootImage background = getBackground();
        background.setColor(Color.WHITE);
        background.fill();
        background.setColor(Color.BLACK);

        // Draw thin lines
        for (int i = 0; i <= 9; i++) {
            int pos = i * 50;
            background.drawLine(pos, 0, pos, 450);
            background.drawLine(0, pos, 450, pos);
        }

        // Draw thicker lines
        for (int i = 0; i <= 9; i += 3) {
            int pos = i * 50;
            for (int j = -1; j <= 1; j++) {
                background.drawLine(pos + j, 0, pos + j, 450);
                background.drawLine(0, pos + j, 450, pos + j);
            }
        }
    }

    private void createCells()
    {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                int num = puzzle[r][c];
                boolean fixed = (num != 0);  // pre-filled numbers can't be changed
                Cell cell = new Cell(num, fixed);
                addObject(cell, c * 50 + 25, r * 50 + 25);
                cells[r][c] = cell;
            }
        }
    }

    public void selectCell(Cell cell)
    {
        selectedCell = cell;
    }

    public void act()
{
    // Toggle note-taking mode
    if (Greenfoot.isKeyDown("n")) {
        noteMode = !noteMode;
        Greenfoot.delay(10); // prevent rapid toggling
        System.out.println("Note mode: " + noteMode);
    }

    if (selectedCell != null) {
        for (int k = 1; k <= 9; k++) {
            if (Greenfoot.isKeyDown(Integer.toString(k))) {
                if (noteMode) {
                    selectedCell.toggleNoteNumber(k);
                } else {
                    selectedCell.setValue(k);
                    selectedCell = null;
                }
                Greenfoot.delay(10); // avoid key repeat
                return;
            }
        }

        if (Greenfoot.isKeyDown("0") || Greenfoot.isKeyDown("backspace")) {
            selectedCell.setValue(0);
            selectedCell = null;
            Greenfoot.delay(10);
        }
    }
}

}
