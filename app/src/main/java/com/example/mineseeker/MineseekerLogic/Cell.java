package com.example.mineseeker.MineseekerLogic;

import java.util.*;
import android.graphics.*;

public class Cell {
    public int xPos;
    public int yPos;
    public boolean isRevealed;
    public boolean isMine;
    private ArrayList<Cell> neighbours;
    private int distance;

    public Cell(int x, int y, boolean isMine){
        this.xPos = x;
        this.yPos = y;
        this.isMine = isMine;
        this.isRevealed = false;
        this.neighbours = new ArrayList<Cell>();
    }
    public void addNeighbour(Cell neighbour) {
        this.neighbours.add(neighbour);
    }

    public ArrayList<Cell> getNeighbours() {
        return this.neighbours;
    }

    public void reveal(int range) {
        this.isRevealed = true;
        for (Cell n : neighbours) {
            n.reveal(range - 1);
        }

    }

    public int getDistance() {
        return this.distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getX() {
        return this.xPos;
    }
    public int getY() {
        return this.yPos;
    }
}
