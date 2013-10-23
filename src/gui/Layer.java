/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LarsAksel
 */
public class Layer {
    private String description;
    private List<Drawable> sprites;
    
    public Layer() {
        this.sprites = new ArrayList<>();
        this.description = "placeholder";
    }
    
    public Layer(String desc) {
        this.sprites = new ArrayList<>();
        this.description = desc;
    }
    
    public void addSprite(Drawable s) {
        this.sprites.add(s);
    }
    
    public void drawSprites() {
        for (Drawable s : sprites) {
            s.draw();
        }
    }
    
    public void removeSprite(int indeks) {
        this.sprites.remove(indeks);
    }
        
    public Drawable getSprite(int indeks) {
        return this.sprites.get(indeks);
    }
    
    public void setSprite(List drawables) {
        this.sprites = drawables;
    }
    
    @Override
    public String toString() {
        return "Description: \"" + this.description + "\" ,number of sprites: " + sprites.size();
    }
}
