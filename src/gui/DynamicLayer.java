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
    private ArrayList<Button> buttons;
    
    public DynamicLayer() {
        this.buttons = new ArrayList<>();
    }
    
    public int addButton(Button b) {
        this.buttons.add(b);
        return this.buttons.size() - 1;
    }
    
    public Button checkButtonState(int xPos, int yPos, boolean isClicked) {
        for (Button b : buttons) {
            Button b2 = b.checkButtonState(xPos, yPos, isClicked);
            if (b2 != null) return b2;
        }
        return null;
    }
    
    public void drawButtons() {
        for (Button b : buttons) {
            b.draw();
        }
    }
}
