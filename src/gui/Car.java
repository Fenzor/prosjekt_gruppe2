/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import org.lwjgl.util.vector.Vector2f;


/**
 *
 * @author Lars Aksel
 */
public class Car extends Sprite {
    private Vector2f moveDirection;
    private float initX;
    private float initY;
    
   
    public Car(Car c) {
        super(c.xPos, c.yPos, c.filetype, c.filepath);
        this.moveDirection = c.moveDirection;
        this.initX = c.initX;
        this.initY = c.initY;
    }
    
    public Car(int xPos, int yPos, String filetype, String filepath, Vector2f v) {
        super(xPos, yPos, filetype, filepath);
        this.moveDirection = v;
        this.initX = xPos;
        this.initY = yPos;
    }
    
    public boolean update(float delta, float windowWidth, float windowHeight) {
        if (!isInside(0, 0, windowWidth, windowHeight)) return false;
        float speed = 0.06f;
        this.xPos += moveDirection.x * speed * delta;
        this.yPos += moveDirection.y * speed * delta;
        super.draw();
        return true;
    }
    
    public void refresh() {
        this.xPos = initX;
        this.yPos = initY;
    }
}
