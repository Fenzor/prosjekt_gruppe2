/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import graphics.Drawable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LarsAksel
 */
public class Layer {
    private String description;
    private List<Drawable> drawables;
    
    public Layer() {
        this.drawables = new ArrayList<>();
        this.description = "placeholder";
    }
    
    public Layer(String desc) {
        this.drawables = new ArrayList<>();
        this.description = desc;
    }
    
    public void addSprite(Drawable s) {
        this.drawables.add(s);
    }
    
    public void drawAll() {
        for (Drawable s : drawables) {
            s.draw();
        }
    }
    
    public void removeDrawable(int indeks) {
        this.drawables.remove(indeks);
    }
        
    public Drawable getDrawable(int indeks) {
        return this.drawables.get(indeks);
    }
    
    public void setDrawable(List drawables) {
        this.drawables = drawables;
    }
    
    public int getSizeOfDrawables() {
        return this.drawables.size();
    }
    
    @Override
    public String toString() {
        return "Description: \"" + this.description + "\" ,number of sprites: " + drawables.size();
    }
}
