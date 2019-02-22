package com.example.mineseeker.MineseekerLogic;

public class Game {
    public Board gameBoard;
    private int numRows;
    private int numCols;
    private int bombCount;
    private boolean isGameOver;
    private int totalClicks;


    public Game(int numRows, int numCols, int bombCount) {
        this.gameBoard = new Board(numRows, numCols, bombCount);
        this.numRows = numRows;
        this.numCols = numCols;
        this.bombCount = bombCount;
    }

    public int getUIBombCount() {
        return gameBoard.getBombCount();
    }

    public int maxClickCounts() {
        return getUIBombCount() * 2;
    }

    public boolean checkIsBomb(int rows, int cols) {
        return gameBoard.grid[rows][cols].isMine;
    }

    public void start() {
        this.isGameOver = false;
        this.gameBoard.reset();

    }

    public int getNearestMine(int row, int col) {
        return gameBoard.grid[row][col].getDistance();
    }

    // 0 = loser
    // 1 = winner
}



// lose condition: if cells clicked is greater than given clicks
// win condition: find all cells within a given number of clicks (2x number of bombs)
// set number