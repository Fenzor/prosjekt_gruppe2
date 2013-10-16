/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.ArrayList;

/**
 *
 * @author LarsAksel
 */
public class Window {
    private ArrayList<Layer> layers;
    private ArrayList<DynamicLayer> dLayers;

    public Window(Window window) {
        this.layers = window.layers;
        this.dLayers = window.dLayers;
    }
     
    public Window() {
        this.layers = new ArrayList<>();
        this.dLayers = new ArrayList<>();
    }
    
    /*
     * Adds an new Layer to be drawn
     */
    public int addLayer() {
        this.layers.add(new Layer());
        return this.layers.size() - 1;
    }
    
    public int addDynamicLayer() {
        this.dLayers.add(new DynamicLayer());
        return this.dLayers.size() - 1;
    }
    
    /*
     * Uses index to determine which layer to send the sprite to...
     */
    public void addSpriteToLayer(int index, Sprite s) {
        this.layers.get(index).addSprite(s);
    }
    
    public void addButtonToLayer(int index, Button b) {
        this.dLayers.get(index).addButton(b);
    }
    
    /*
     * Returns number of layers within this window...
     */
    public int numberOfLayers() {
        return this.layers.size();
    }
    
    public void drawAll() {
        for (Layer l : layers) {
            l.drawSprites();
        }
        for (DynamicLayer d : dLayers) {
            d.drawButtons();
        }
    }
}
