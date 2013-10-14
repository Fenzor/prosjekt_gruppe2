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
    protected float xPos;
    protected float yPos;
    protected float sizeX;
    protected float sizeY;

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

    public float getX() {
        return xPos;
    }

    public void setX(int xPos) {
        this.xPos = xPos;
    }

    public float getY() {
        return yPos;
    }

    public void setY(int yPos) {
        this.yPos = yPos;
    }

    public float getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public float getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }
    
    public void scaleSize(float x, float y) {
        this.sizeX = this.sizeX * x;
        this.sizeY = this.sizeY * y;
    }
    
    public boolean isInside(int xPos, int yPos) {
        return (xPos >= this.getX() && xPos <= (this.getX() + this.getSizeX()) && yPos >= this.getY() && yPos <= (this.getY() + this.getSizeY()));
    }
}
