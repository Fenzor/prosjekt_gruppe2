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
    
   
    public Car(int xPos, int yPos, String a, String b) {
        super(xPos, yPos, a, b);
        this.moveDirection = new Vector2f(-2, -1);
    }
    
    @Override
    public void draw() {
        float speed = 3f;
        this.xPos += moveDirection.x * speed;
        this.yPos += moveDirection.y * speed;
        super.draw();
    }
}
