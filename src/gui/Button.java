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
    private boolean wasClicked;
    private boolean wasInside;
    private Texture defaultButtonState;
    private Texture hoveredButtonState;
    private Texture clickedButtonState;
    private final TextType textType;
    private final String buttonText;
    private final float textOffsetX;
    private final float textOffsetY;

    public Button(float xPos, float yPos, float sizeX, float sizeY) {
        super(xPos, yPos, sizeX, sizeY);
        this.textType = null;
        this.buttonText = "placeholder";
        this.textOffsetX = sizeX / 2;
        this.textOffsetY = sizeY / 2;
    }

    public Button(float xPos, float yPos, float sizeX, float sizeY, TextType text) {
        super(xPos, yPos, sizeX, sizeY);
        this.textType = text;
        this.buttonText = "placeholder";
        this.textOffsetX = sizeX / 2;
        this.textOffsetY = sizeY / 2;
    }

    public Button(float xPos, float yPos, float sizeX, float sizeY, TextType textType, String buttonText) {
        super(xPos, yPos, sizeX, sizeY);
        this.textType = textType;
        this.buttonText = buttonText;
        this.textOffsetX = sizeX / 2;
        this.textOffsetY = sizeY / 2;
    }
    
    public boolean loadAllStates(String filetype, String path) {
        try {
            this.image = TextureLoader.getTexture(filetype, ResourceLoader.getResourceAsStream(path), GL11.GL_NEAREST);
            this.defaultButtonState = this.image;
            this.hoveredButtonState = this.image;
            this.clickedButtonState = this.image;
        } catch (IOException e) {
            System.err.println("Trouble loading texture-assets!!!");
            e.printStackTrace();
            return false;
        }
        return true;
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

    public Button checkButtonState(int xPos, int yPos, boolean isClicked) {
        if (this.isInside(xPos, yPos)) {
            if (isClicked && !this.wasInside) {
                this.wasClicked = true;
                this.setClickedState();
            } else if (this.wasClicked) {
                this.wasClicked = false;
                this.setHoveredState();
                return this;
            } else this.setHoveredState();
        } else this.setDefaultState();
        return null;
    }

    public String getButtonText() {
        return buttonText;
    }
    
    public void draw(float xPosOffset, float yPosOffset, String text) {
        super.draw();
        this.textType.draw(xPos + xPosOffset, yPos + yPosOffset, text);
    }

    @Override
    public void draw() {
        super.draw();
        if (this.textType != null) this.textType.draw(xPos + this.sizeX/2, yPos + this.sizeY/2, buttonText);
    }
}
