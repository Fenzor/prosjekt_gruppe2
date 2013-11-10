/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author Lars Aksel
 */
public class TextType {
    private boolean antiAliased;
    private TrueTypeFont ttFont;
    private Color textColor;
    private float fontSize;
    private int alignment;
    
    
    public TextType(float xPos, float yPos, String text, String fontPath, float fontSize, boolean antiAliased) {
        this.fontSize = fontSize;
        this.antiAliased = antiAliased;
        this.textColor = new Color(1,1,1);
        try {
            InputStream inputStream = ResourceLoader.getResourceAsStream(fontPath);
            Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            awtFont = awtFont.deriveFont(fontSize); // set font size
            ttFont = new TrueTypeFont(awtFont, antiAliased, null);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }
    
    public TextType(String fontPath, float fontSize, boolean antiAliased, Color textColor, int alignment) {
        this.fontSize = fontSize;
        this.antiAliased = antiAliased;
        this.textColor = textColor;
        this.alignment = alignment;
        try {
            Font awtFont;
            try (InputStream inputStream = ResourceLoader.getResourceAsStream(fontPath)) {
                awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            }
            awtFont = awtFont.deriveFont(fontSize); // set font size
            ttFont = new TrueTypeFont(awtFont, antiAliased, null);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }
    
    public void draw(float xPos, float yPos, String text) {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glColor3f(this.textColor.r, this.textColor.g, this.textColor.b);
        ttFont.drawString(xPos, yPos, text, 1, 1, alignment);
        GL11.glColor3f(1, 1, 1);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }
}
