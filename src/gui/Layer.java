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
public class Layer {
    protected String description;
    protected ArrayList<Sprite> sprites;
    
    public Layer() {
        this.sprites = new ArrayList<>();
        this.description = "placeholder";
    }
    
    public Layer(String desc) {
        this.sprites = new ArrayList<>();
        this.description = desc;
    }
    
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }
    
    public void drawSprites() {
        for (Sprite s : sprites) {
            s.draw();
        }
    }
    
    @Override
    public String toString() {
        return "Description: \"" + this.description + "\" ,number of sprites: " + sprites.size();
    }
}
