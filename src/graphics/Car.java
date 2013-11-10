/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import graphics.Sprite;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Lars Aksel
 */
public class Car extends Sprite {

    private Vector2f moveDirection;
    private float initX;
    private float initY;
    private float speed;
    private boolean isExploded;
    private Animation animate;
    private SpriteSheet ss;
    private static final int duration = 25;

    public Car(Car c) {
        super(c.xPos, c.yPos, c.sizeX, c.sizeY, c.image);
        this.moveDirection = new Vector2f(c.moveDirection);
        this.initX = c.initX;
        this.initY = c.initY;
        this.speed = c.speed;
        this.isExploded = c.isExploded;
        this.ss = c.ss;
        this.animate = new Animation(c.ss, duration);
        this.animate.setAutoUpdate(false);
        this.animate.setLooping(false);
    }

    public Car(float xPos, float yPos, float sizeX, float sizeY, float speed, String filetype, String filepath, Vector2f v) {
        super(xPos, yPos, sizeX, sizeY, filetype, filepath);
        this.moveDirection = v;
        this.initX = xPos;
        this.initY = yPos;
        this.speed = speed;
        this.isExploded = false;
        
        try {
            this.ss = new SpriteSheet("res/images/explosion.png", 100, 100);
        } catch (SlickException e) {

        }
        this.animate = new Animation(this.ss, duration);
        this.animate.setAutoUpdate(false);
        this.animate.setLooping(false);
    }

    public boolean update(float delta) {
        if (!isExploded) {
            this.xPos += moveDirection.x * (speed * (float) delta);
            this.yPos += moveDirection.y * (speed * (float) delta);
            return true;
        } else if (isExploded && !animate.isStopped()) {
            animate.update((long) delta);
            return true;
        }
        return false;
    }

    public void refresh() {
        this.xPos = initX;
        this.yPos = initY;
    }

    public void explode() {
        if (!isExploded) {
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                new Sound("res/sfx/explosion.ogg").play(1, 0.5f);
                            } catch (SlickException ex) {
                                //Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }).start();
        }

        this.isExploded = true;
    }

    @Override
    public void draw() {
        if (this.animate.getFrame() < this.animate.getFrameCount() / 20) super.draw();
        if (animate.getFrameCount() > 0 && animate.getFrame() != 0) {
            animate.draw(xPos, yPos, sizeX, sizeX);
        }
        //if (animate.getFrameCount() - 1 == animate.getFrame()) animate.stop();
    }
}
