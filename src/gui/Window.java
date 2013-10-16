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
}
