/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import java.awt.event.MouseAdapter;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;

/**
 *
 * @author LarsAksel
 */
public class InputHandler implements Runnable {
    private boolean isRunning;
    private Button pressedButton;
    private Thread inputThread;
    private Window window;
    private int carLayer;
    
    public InputHandler(Window window) {
        this.window = window;
        this.inputThread = new Thread(this, "inputThread");
        this.carLayer = -1;
    }
    
    public void init() {
        this.isRunning = true;
        this.inputThread.start();
    }
    
    @Override
    public void run() {
        while (isRunning) {
            if (Mouse.isCreated()) {
                checkButtons();
                if (!window.getDynLayer().clickOnButton(Mouse.getX(), Mouse.getY())) {
                    checkLayer();
                }
            }            
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                
            }
        }
    }
    
    public synchronized void setWindow(Window win) {
        this.window = win;
        this.carLayer = -1;
    }
    
    private synchronized void checkLayer() {
        if (this.carLayer < 0) return;
        if (window.getLayer(this.carLayer) instanceof CarPool) {
            if (Mouse.isButtonDown(0)) {
                ((CarPool) window.getLayer(this.carLayer)).checkCars(Mouse.getX(), Mouse.getY());
            }

        }
    }
    
    public synchronized void setLayer(int layer) {
        this.carLayer = layer;
    }
    
    private synchronized boolean checkButtons() {
        Button b = window.getDynLayer().checkButtonState(Mouse.getX(), Mouse.getY(), Mouse.isButtonDown(0));
        if (b != null) {
            this.setButtonPressed(b);
            return true;
        } return false;
    }
    
    private synchronized void setButtonPressed(Button b) {
        this.pressedButton = b;
        //System.out.println("Mouse clicked: " + b.getButtonText());
        
    }
    
    public synchronized Button getButtonPressed() {
        Button b = this.pressedButton;
        this.pressedButton = null;
        return b;       
    }
    
    public void destroy() {
        this.isRunning = false;
    }
}
