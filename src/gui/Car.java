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
    private float speed;
    
   
    public Car(Car c) {
        super(c.xPos, c.yPos, c.sizeX, c.sizeY, c.image);
        this.moveDirection = new Vector2f(c.moveDirection);
        this.initX = c.initX;
        this.initY = c.initY;
        this.speed = c.speed;
    }
    
    public Car(float xPos, float yPos, float speed, String filetype, String filepath, Vector2f v) {
        super(xPos, yPos, filetype, filepath);
        this.moveDirection = v;
        this.initX = xPos;
        this.initY = yPos;
        this.speed = speed;
    }
    
    public Car(float xPos, float yPos, float sizeX, float sizeY, float speed, String filetype, String filepath, Vector2f v) {
        super(xPos, yPos, sizeX, sizeY, filetype, filepath);
        this.moveDirection = v;
        this.initX = xPos;
        this.initY = yPos;
        this.speed = speed;
    }
    
    public void update(float delta) {
        this.xPos += moveDirection.x * (speed * (float) delta);
        this.yPos += moveDirection.y * (speed * (float) delta);
        //super.draw();
    }
    
    public void refresh() {
        this.xPos = initX;
        this.yPos = initY;
    }
}
