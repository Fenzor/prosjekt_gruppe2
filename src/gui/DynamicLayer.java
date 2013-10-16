/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.ArrayList;
import org.lwjgl.input.Mouse;

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
    
    public Button isInside() {
        boolean isClicked = Mouse.isButtonDown(0);
        int xPos = Mouse.getX();
        int yPos = Mouse.getY();
        for (Button b : buttons) {
            if (b.isInside(xPos, yPos)) {                
                if (isClicked) {
                    b.setClickedState();
                    return b;
                }
                b.setHoveredState();
            } else b.setDefaultState();
        }
        return null;
    }
    
    public void drawButtons() {
        for (Button b : buttons) {
            b.draw();
        }
    }
}
