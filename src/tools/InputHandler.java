/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tools;

import graphics.Button;
import gui.CarPool;
import gui.Window;
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
    private int xPos;
    private int yPos;
    private boolean mouseClicked;
    
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
                this.xPos = Mouse.getX();
                this.yPos = Mouse.getY();
                this.mouseClicked = Mouse.isButtonDown(0);
                checkButtons();
                if (!window.getDynLayer().clickOnButton(xPos, yPos)) {
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
            if (mouseClicked) {
                ((CarPool) window.getLayer(this.carLayer)).checkCars(xPos, yPos);
            }

        }
    }
    
    public synchronized void setLayer(int layer) {
        this.carLayer = layer;
    }
    
    private synchronized boolean checkButtons() {
        Button b = window.getDynLayer().checkButtonState(xPos, yPos, mouseClicked);
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
