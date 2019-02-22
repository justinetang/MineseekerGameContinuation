package com.example.mineseeker.MineseekerLogic;

import java.util.*;
import android.graphics.*;
import android.content.*;
import android.view.*;

import com.example.mineseeker.R;

public class Board {

    public Cell[][] grid;
    private int numRows;
    private int numCols;
    private int bombCount;
    private int cellsRevealed;

    public Board(int numRows, int numCols, int bombCount) {
        this.grid = new Cell[numRows][numCols];
        this.numRows = numRows;
        this.numCols = numCols;
        this.bombCount = bombCount;
    }

    public void reset() {
        for(int i = 0; i < this.grid.length; i++) {
            for(int j = 0; j < this.grid.length; j++) {
                this.grid[i][j] = new Cell(i, j, false);
                this.addCellNeighbors(grid[i][j], i, j);
            }
        }
        this.shuffleBombs(this.bombCount);
        this.calculateCellDistance();
        this.cellsRevealed = 0;
    }

    public void shuffleBombs(int bombCount) {
        boolean spotAvailable = true;
        Random random = new Random();
        int row;
        int column;
        for(int i = 0; i < bombCount; i++) {
            do {
                column = random.nextInt(numCols-1);
                row = random.nextInt(numRows-1);
                spotAvailable = this.grid[column][row].isMine;
            } while (spotAvailable);
            this.grid[column][row].isMine = true;
        }
    }

    public void addCellNeighbors(Cell c, int x, int y ){
        if(!((x+1) > numCols)){
            c.addNeighbour(grid[x+1][y]);
        }
        if(!((x-1) < 0)){
            c.addNeighbour(grid[x-1][y]);
        }
        if(!((y+1) > numRows)){
            c.addNeighbour(grid[x][y+1]);
        }
        if(!((y-1) < 0)){
            c.addNeighbour(grid[x][y-1]);
        }
    }

    public int getRevealedCount() {
        this.cellsRevealed = 0;
        for (int x = 0; x < this.grid.length; x++) {
            for (int y = 0; y < this.grid.length; y++) {
                if(grid[x][y].isRevealed && !grid[x][y].isMine){
                    this.cellsRevealed++;
                }
            }
        }
        return cellsRevealed;
    }

    // Calculate minimum distance of each cell in board from nearest mine
    public void calculateCellDistance() {
        boolean[][] visitedCells = new boolean[numRows][numCols];
        for (int x = 0; x < this.grid.length; x++) {
            for (int y = 0; y < this.grid.length; y++) {
                visitedCells[x][y] = false;
            }
        }

        for (int x = 0; x < this.grid.length; x++) {
            for (int y = 0; y < this.grid.length; y++) {
                grid[x][y].setDistance(getNearestMine(grid[x][y],0, visitedCells));
            }
        }
    }

    public int getNearestMine(Cell c, int counter, boolean[][] visitedCells) {
        if(c.isMine) {
            return counter;
        }
        Queue <Cell> queue = new LinkedList<>();
        // populate queue with eligible moves (non-visited)
        for (Cell neighbour: c.getNeighbours()){
            if(!visitedCells[neighbour.getX()][neighbour.getY()]) {
                queue.add(neighbour);
            }
        }
        // while queue isn't empty, get next neighbourS, and search.
        while(queue.peek() != null){
            Cell next = queue.remove();
            return getNearestMine(next, counter++, visitedCells);
        }
        return 0;
    }

    public void showBombs(Cell c) {
        if(c.isMine) {
            //c.reveal();
        }
    }

    public int getBombCount() {
        return this.bombCount;
    }

}
