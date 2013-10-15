/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.ArrayList;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 *
 * @author LarsAksel
 */
public class Window {
    private int windowWidth;
    private int windowHeight;
    private boolean isFullscreen;
    private ArrayList<Layer> layers;

    public Window(Window window) {
        this.windowWidth = window.windowWidth;
        this.windowHeight = window.windowHeight;
        this.isFullscreen = window.isFullscreen;
        this.layers = window.layers;
    }
     
    public Window(int windowWidth, int windowHeight, boolean isFullscreen) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.isFullscreen = isFullscreen;
        this.layers = new ArrayList<>();
    }
    
    /*
     * Adds an new Layer to be drawn
     */
    public void addLayer() {
        this.layers.add(new Layer());
    }
    
    /*
     * Uses index to determine which layer to send the sprite to...
     */
    public void addSpriteToLayer(int index, Sprite s) {
        this.layers.get(index).addSprite(s);
    }
    
    /*
     * Returns number of layers within this window...
     */
    public int numberOfLayers() {
        return this.layers.size();
    }
    
    /*
     * Returns width of window...
     */
    public int getWindowWidth() {
        return windowWidth;
    }

    /*
     * Returns height of window...
     */
    public int getWindowHeight() {
        return windowHeight;
    }
    
    /*
     * Is used to check for global input, this may be moved...
     */    
    protected void checkGlobalInput() {
        if (Keyboard.isKeyDown(Keyboard.KEY_F11)) {
            this.isFullscreen = !this.isFullscreen;
            this.setDisplayMode(this.windowWidth, this.windowHeight, this.isFullscreen);
        }
    }
        
    /**
    * Set the display mode to be used 
    * 
    * @param width The width of the display required
    * @param height The height of the display required
    * @param fullscreen True if we want fullscreen mode
    */
   public void setDisplayMode(int width, int height, boolean fullscreen) {

       // return if requested DisplayMode is already set
       if ((Display.getDisplayMode().getWidth() == width) && 
           (Display.getDisplayMode().getHeight() == height) && 
           (Display.isFullscreen() == fullscreen)) {
               return;
       }

       try {
           DisplayMode targetDisplayMode = null;

           if (fullscreen) {
               DisplayMode[] modes = Display.getAvailableDisplayModes();
               int freq = 0;

               for (int i=0;i<modes.length;i++) {
                   DisplayMode current = modes[i];

                   if ((current.getWidth() == width) && (current.getHeight() == height)) {
                       if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
                           if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
                               targetDisplayMode = current;
                               freq = targetDisplayMode.getFrequency();
                           }
                       }

                       // if we've found a match for bpp and frequence against the 
                       // original display mode then it's probably best to go for this one
                       // since it's most likely compatible with the monitor
                       if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) &&
                           (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
                               targetDisplayMode = current;
                               break;
                       }
                   }
               }
           } else {
               targetDisplayMode = new DisplayMode(width,height);
           }

           if (targetDisplayMode == null) {
               System.out.println("Failed to find value mode: "+width+"x"+height+" fs="+fullscreen);
               return;
           }

           Display.setDisplayMode(targetDisplayMode);
           Display.setFullscreen(fullscreen);

       } catch (LWJGLException e) {
           System.out.println("Unable to setup mode "+width+"x"+height+" fullscreen="+fullscreen + e);
       }
   }
}
