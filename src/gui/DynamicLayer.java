/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.ArrayList;

/**
 *
 * @author Lars Aksel
 */
public class DynamicLayer {
    ArrayList<Button> buttons;
    
    public DynamicLayer() {
        this.buttons = new ArrayList<>();
    }
    
    public int addButton(Button b) {
        this.buttons.add(b);
        return this.buttons.size() - 1;
    }
    
    public Button isInside(int xPos, int yPos) {
        for (Button b : buttons) {
            if (b.isInside(xPos, yPos)) return b;
        }
        return null;
    }
    
    public void drawButtons() {
        for (Button b : buttons) {
            b.draw();
        }
    }
}
