/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author Lars Aksel
 */
public class AABB {
    protected int xPos;
    protected int yPos;
    protected int sizeX;
    protected int sizeY;

    public AABB(int xPos, int yPos, int sizeX, int sizeY) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }
    
    public AABB(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.sizeX = 0;
        this.sizeY = 0;
    }

    public int getX() {
        return xPos;
    }

    public void setX(int xPos) {
        this.xPos = xPos;
    }

    public int getY() {
        return yPos;
    }

    public void setY(int yPos) {
        this.yPos = yPos;
    }

    public int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }
    
    public boolean isInside(int xPos, int yPos) {
        return (xPos >= this.getX() && xPos <= (this.getX() + this.getSizeX()) && yPos >= this.getY() && yPos <= (this.getY() + this.getSizeY()));
    }
}
