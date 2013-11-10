/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import graphics.Button;
import graphics.Drawable;
import java.util.ArrayList;

/**
 *
 * @author LarsAksel
 */
public class Window {
    private ArrayList<Layer> layers;
    private DynamicLayer dLayer;

    public Window(Window window) {
        this.layers = window.layers;
        this.dLayer = window.dLayer;
    }
     
    public Window() {
        this.layers = new ArrayList<>();
        this.dLayer = new DynamicLayer();
    }
    
    /*
     * Adds an new Layer to be drawn
     */
    public int addLayer() {
        this.layers.add(new Layer());
        return this.layers.size() - 1;
    }
    
    /*
     * Adds an new Layer to be drawn
     */
    public int addLayer(Layer l) {
        this.layers.add(l);
        return this.layers.size() - 1;
    }
    
    /*
     * Uses index to determine which layer to send the sprite to...
     */
    public void addDrawableToLayer(int index, Drawable s) {
        this.layers.get(index).addSprite(s);
    }
    
    public void addButtonToLayer(Button b) {
        this.dLayer.addButton(b);
    }
    
    public DynamicLayer getDynLayer() {
        return this.dLayer;
    }
    
    /*
     * Returns number of layers within this window...
     */
    public int numberOfLayers() {
        return this.layers.size();
    }
    
    public Layer getLayer(int indeks) {
        return this.layers.get(indeks);
    }
    
    public void drawAll() {
        for (Layer l : layers) {
            l.drawAll();
        }
        dLayer.drawButtons();
    }
}
