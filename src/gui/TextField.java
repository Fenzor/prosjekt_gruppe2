/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import graphics.Drawable;
import graphics.AABB;

/**
 *
 * @author Lars Aksel
 */
public class TextField extends AABB implements Drawable {
    private String text;
    private TextType tt;

    public TextField(float xPos, float yPos, String text, TextType tt) {
        super(xPos, yPos);
        this.text = text;
        this.tt = tt;
    }
    
    public void draw() {
        tt.draw(xPos, yPos, text);
    }

    public void setText(String text) {
        this.text = text;
    }
}
