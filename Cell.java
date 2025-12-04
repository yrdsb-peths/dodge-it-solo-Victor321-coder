import greenfoot.*;  

public class Cell extends Actor {
    private int value = 0;          // 0 = empty
    private boolean fixed = false;  // prefilled cells can't be edited
    private boolean[] notes = new boolean[10];  // track small numbers (1–9)
    private boolean noteMode = false;           // toggled from the world

    public Cell(int val, boolean isFixed) {
        value = val;
        fixed = isFixed;
        updateImage();
    }

    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            ((myworld)getWorld()).selectCell(this);
        }
    }

    public void setValue(int val) {
        if (!fixed) {
            value = val;
            clearNotes();
            updateImage();
        }
    }

    public void toggleNoteMode(boolean mode) {
        noteMode = mode;
    }

    public void toggleNoteNumber(int num) {
        if (!fixed && num >= 1 && num <= 9) {
            notes[num] = !notes[num];
            updateImage();
        }
    }

    public void clearNotes() {
        for (int i = 1; i <= 9; i++) {
            notes[i] = false;
        }
    }

    public void updateImage() {
        GreenfootImage img = new GreenfootImage(50, 50);
        img.setColor(Color.WHITE);
        img.fill();
        img.setColor(Color.BLACK);
        img.drawRect(0, 0, 49, 49);

        if (value != 0) {
            // Draw big number
            Color textColor = fixed ? Color.BLUE : Color.BLACK;
            GreenfootImage text = new GreenfootImage("" + value, 26, textColor, null);
            img.drawImage(text, (img.getWidth() - text.getWidth()) / 2, (img.getHeight() - text.getHeight()) / 2);
        } else {
            // Draw notes (small 1–9)
            img.setColor(Color.GRAY);
            int size = 12; // smaller text size
            for (int n = 1; n <= 9; n++) {
                if (notes[n]) {
                    int row = (n - 1) / 3;
                    int col = (n - 1) % 3;
                    GreenfootImage smallNum = new GreenfootImage("" + n, size, Color.GRAY, null);
                    img.drawImage(smallNum, col * 16 + 4, row * 16 + 2);
                }
            }
        }

        setImage(img);
    }
}
