import se.lth.cs.ptdc.window.SimpleWindow;

public class Main {

    public static void main(String[] args) {
        int ruleNo = 30;  // Must be number in range 0-255

        CellGrid cg = new CellGrid(400);
        SimpleWindow w = new SimpleWindow(800, 400, "Cellular Automaton");
        cg.generateAllRows(ruleNo);
        cg.draw(w);
    }
}

class CellGrid {
    int height;
    int width;
    int currentRow;
    boolean[][] grid;

    public CellGrid(int rows) {
        this.width = rows*2;
        this.height = rows;
        this.currentRow = 0;
        this.grid = new boolean[width][height];
        this.grid[rows][0] = true;
    }

    public void draw(SimpleWindow w) {
        for (int y=0; y<height; y++) {
            for (int x=0; x<width; x++) {
                if (grid[x][y]) {
                    w.moveTo(x,y);
                    w.lineTo(x,y);
                }
            }
        }
    }

    public void printRow() {
        for (int x=0; x<width; x++) {
            System.out.print(grid[x][currentRow] + "\t");
        }
        System.out.print("\n");
    }

    public void generateAllRows(int ruleNumber) {
        generateAllRows(getRule(ruleNumber));
    }

    public void generateAllRows(boolean[] rules) {
        while (currentRow < height-1) {
            generateNextRow(rules);
        }
    }

    public void generateNextRow(int ruleNumber) {
        generateNextRow(getRule(ruleNumber));
    }

    public void generateNextRow(boolean[] rules) {
        currentRow += 1;
        int y = currentRow;
        for (int x=0; x<width; x++) {
            grid[x][y] = getState(x, y, rules);
        }
    }

    public boolean getState(int x, int y, boolean[] rules) {
        boolean[] a;
        if (x == 0) {
            a = new boolean[]{false, grid[x][y-1], grid[x+1][y-1]};
        } else if (x == width-1) {
            a = new boolean[]{grid[x-1][y-1], grid[x][y-1], false};
        } else {
            a = new boolean[]{grid[x-1][y-1], grid[x][y-1], grid[x+1][y-1]};
        }
        return ruleX(a, rules);
    }

    public int boolArray2int(boolean[] a) {
        int n = 0;
        for (boolean b : a) {
            n = (n << 1) + (b ? 1 : 0);
        }
        return n;
    }

    /**
     * Horribly written, I know
     *
     * @param n
     * @return
     */
    public boolean[] int2BoolArray(int n) {
        char[] charArray = Integer.toBinaryString(n).toCharArray();
        boolean[] boolArray = new boolean[charArray.length];
        for (int i=0; i<charArray.length; i++) {
            int a = Character.getNumericValue(charArray[i]);
            boolArray[i] = (a == 1);
        }
        return boolArray;
    }

    public boolean[] getRule(int n) {
        boolean[] rules = int2BoolArray(n);
        return rules;
    }

    public boolean ruleX(boolean[] ancestors, boolean[] rules) {
        int ancestorRule = boolArray2int(ancestors);
        try {
            return rules[rules.length-1-ancestorRule];
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

}