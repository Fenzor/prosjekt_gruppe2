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
    
    public InputHandler(Window window) {
        this.window = window;
        this.inputThread = new Thread(this, "inputThread");
    }
    
    public void init() {
        this.isRunning = true;
        this.inputThread.start();
    }
    
    @Override
    public void run() {
        while (isRunning) {
            if (Mouse.isCreated()) {
                Button b = window.getDynLayer().checkButtonState(Mouse.getX(), Mouse.getY(), Mouse.isButtonDown(0));
                if (b != null) this.setButtonPressed(b);
            }            
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                
            }
        }
    }
    
    private synchronized void setButtonPressed(Button b) {
        this.pressedButton = b;
        System.out.println("Mouse clicked: " + b.getButtonText());
        
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
