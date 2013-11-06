/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import org.newdawn.slick.geom.Rectangle;


/**
 *
 * @author Lars Aksel
 */
public class AABB {
    protected float xPos;
    protected float yPos;
    protected float sizeX;
    protected float sizeY;

    public AABB(float xPos, float yPos, float sizeX, float sizeY) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }
    
    public AABB(float xPos, float yPos) {
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

    public void setSizeX(float sizeX) {
        this.sizeX = sizeX;
    }

    public float getSizeY() {
        return sizeY;
    }

    public void setSizeY(float sizeY) {
        this.sizeY = sizeY;
    }
    
    public void scaleSize(float x, float y) {
        this.sizeX = this.sizeX * x;
        this.sizeY = this.sizeY * y;
    }
    
    public boolean isInside(float xPos, float yPos) {
        return (xPos >= this.getX() && xPos <= (this.getX() + this.getSizeX()) && yPos >= this.getY() && yPos <= (this.getY() + this.getSizeY()));
    }
    
    public boolean isInside(float xPos, float yPos, float sizeX, float sizeY) {
        
        float smallBit = 0.1f;
        return (isInside(xPos + smallBit, yPos + smallBit) || 
                isInside(xPos + smallBit, (yPos + sizeY) - smallBit) || 
                isInside((xPos + sizeX) - smallBit, yPos + smallBit) || 
                isInside((xPos + sizeX) - smallBit, (yPos + sizeY) - smallBit));
        //return ((this.xPos + this.sizeX) >= xPos && this.xPos <= (xPos + sizeX) || (this.yPos + this.sizeY) >= yPos && this.yPos <= (yPos + sizeY));
    }
    public boolean intersects(float xPos, float yPos, float sizeX, float sizeY) {
        Rectangle r = new Rectangle(this.xPos, this.yPos, this.sizeX, this.sizeY);
        Rectangle r2 = new Rectangle(xPos, yPos, sizeX, sizeY);
        return r.intersects(r2);
    }
}
