package com.erikbjare.cellularautomaton;

import se.lth.cs.ptdc.window.SimpleWindow;

public class Main {

    public static void main(String[] args) {
	    CellGrid cg = new CellGrid(400);
        SimpleWindow w = new SimpleWindow(800, 400, "Cellular Automaton");
        cg.generateAllRows();
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

    public void generateAllRows() {
        while (currentRow < height-1) {
            generateNextRow();
        }
    }

    public void generateNextRow() {
        currentRow += 1;
        int y = currentRow;
        for (int x=0; x<width; x++) {
            grid[x][y] = getState(x, y);
        }
    }

    public boolean getState(int x, int y) {
        boolean[] a;
        if (x == 0) {
            a = new boolean[]{false, grid[x][y-1], grid[x+1][y-1]};
        } else if (x == width-1) {
            a = new boolean[]{grid[x-1][y-1], grid[x][y-1], false};
        } else {
            a = new boolean[]{grid[x-1][y-1], grid[x][y-1], grid[x+1][y-1]};
        }
        return rule30(a);
    }

    public int boolArray2int(boolean[] a) {
        int n = 0, l = a.length;
        for (int i = 0; i < l; ++i) {
            n = (n << 1) + (a[i] ? 1 : 0);
        }
        return n;
    }

    public boolean rule30(boolean[] a) {
        int n = boolArray2int(a);
        if (n > 0 && n < 5) {
            return true;
        } else {
            return false;
        }
    }

}