/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.Random;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

/**
 *
 * @author Lars Aksel
 */
public class CarPool extends Layer implements Runnable{
    private Car[] collection;
    private Car[] generated;
    private int minWaiting;
    private int maxWaiting;
    private int maxCars = 10;
    private int windowWidth;
    private int windowHeight;
    private Random rand;
    private Thread thread;
    private boolean isRunning;
    private boolean isPaused;
    private long beforeTime = 0;
    private long afterTime = 0;
    private long deltaTime = 0;

    public CarPool(int windowWidth, int windowHeight, int minWaiting, int maxWaiting, Car... col) {
        this.minWaiting = minWaiting;
        this.maxWaiting = maxWaiting;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.rand = new Random();
        this.collection = col;
        this.generated = new Car[maxCars];
    }
    
    public void init() {
        this.isRunning = true;
        this.isPaused = false;
        this.thread = new Thread(this, "CarThread");
        this.thread.start();
    }
    
    public void run() {
        int waitNextCar = rand.nextInt(maxWaiting - minWaiting) + minWaiting;
        while (isRunning) {            
            beforeTime = getTime();
            for (int i = 0; i < maxCars; i++) {
                Car c = generated[i];
                if (c != null) {
                    // Checks if car is fully inside the window, else destroys the car...
                    if (!c.intersects(0, 0, windowWidth, windowHeight)) {
                        generated[i] = null;
                        break;
                    }
                    
                    // Checks if car collides with other cars...
                    /*
                    boolean isCollided = false;
                    for (int j = 0; j < maxCars; j++) {
                        if (i == j) continue;
                        if (generated[j] != null) {
                            Car c2 = generated[j];
                            if (c.isInside(c2.getX(), c2.getY(), c2.getSizeX(), c2.getSizeY())) {
                                isCollided = true;
                                break;
                            }
                        }
                    }*/
                    //if (!isCollided) c.update(deltaTime);
                    c.update(deltaTime);
                } else if (waitNextCar <= 0) {
                    generated[i] = new Car(collection[rand.nextInt(collection.length)]);
                    waitNextCar = rand.nextInt(maxWaiting - minWaiting) + minWaiting;
                    break;
                }
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                
            }
            afterTime = getTime();
            deltaTime = (afterTime - beforeTime);
            if (isPaused) deltaTime = 0;
            waitNextCar -= deltaTime;
        }
    }
    
    @Override
    public synchronized void drawSprites() {
        for (int i = 0; i < maxCars; i++) {
            if (generated[i] != null) generated[i].draw();
        }
    }
    
    public void destroy() {
        this.isRunning = false;
    }
    
    public void setPaused(boolean value) {
        this.isPaused = value;
    }
    
    /**
     * Get the accurate system time
     *
     * @return The system time in milliseconds
     */
    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }
}
