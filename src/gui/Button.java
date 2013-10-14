/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author Lars Aksel
 */
public class Button extends Sprite {
    private boolean isDefault;
    private boolean isHovered;
    private boolean isClicked;
    private Texture defaultButtonState;
    private Texture hoveredButtonState;
    private Texture clickedButtonState;
    private Text text;
    
    public Button(int xPos, int yPos, int sizeX, int sizeY) {
        super(xPos, yPos, sizeX, sizeY);
    }
    
    public Button(int xPos, int yPos, int sizeX, int sizeY, Text text) {
        super(xPos, yPos, sizeX, sizeY);
        this.text = text;
    }
    
    public boolean loadDefaultButtonState(String filetype, String path) {
        try {
            this.defaultButtonState = TextureLoader.getTexture(filetype, ResourceLoader.getResourceAsStream(path), GL11.GL_NEAREST);  
            this.image = this.defaultButtonState;
        } catch (IOException e) {
            System.err.println("Trouble loading texture-assets!!!");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean loadHoveredButtonState(String filetype, String path) {
        try {
            this.hoveredButtonState = TextureLoader.getTexture(filetype, ResourceLoader.getResourceAsStream(path), GL11.GL_NEAREST);            
        } catch (IOException e) {
            System.err.println("Trouble loading texture-assets!!!");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean loadClickedButtonState(String filetype, String path) {
        try {
            this.clickedButtonState = TextureLoader.getTexture(filetype, ResourceLoader.getResourceAsStream(path), GL11.GL_NEAREST);            
        } catch (IOException e) {
            System.err.println("Trouble loading texture-assets!!!");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean isDefault() {
        return this.isDefault;
    }
    
    public boolean isHovered() {
        return this.isHovered;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public Texture getDefaultButtonState() {
        return defaultButtonState;
    }

    public Texture getHoveredButtonState() {
        return hoveredButtonState;
    }

    public Texture getClickedButtonState() {
        return clickedButtonState;
    }
    
    public void setDefaultState() {
        this.isDefault = true;
        this.isHovered = false;
        this.isClicked = false;
        this.image = defaultButtonState;
    }
    
    public void setHoveredState() {
        this.isDefault = false;
        this.isHovered = true;
        this.isClicked = false;
        this.image = hoveredButtonState;
    }
    
    public void setClickedState() {
        this.isDefault = false;
        this.isHovered = false;
        this.isClicked = true;
        this.image = clickedButtonState;
    }
    
    public void draw(String text, int xPos, int yPos) {
        super.draw();
        this.text.draw(text, (int) this.xPos + xPos, (int) this.yPos + yPos);
    }
}
