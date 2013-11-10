/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import graphics.shader.Shader;
import java.io.IOException;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author Lars Aksel
 */
public class Sprite extends AABB implements Drawable {
    protected Texture image;
    protected Shader shader;
    
    public Sprite(float xPos, float yPos, float sizeX, float sizeY) {
        super(xPos, yPos, sizeX, sizeY);
    }
    
    public Sprite(float xPos, float yPos, String filetype, String path) {
        super(xPos, yPos);
        try {
            this.image = TextureLoader.getTexture(filetype, ResourceLoader.getResourceAsStream(path), GL11.GL_NEAREST);           
        } catch (IOException e) {
            System.err.println("Trouble loading texture-assets!!!");
            e.printStackTrace();
        }
        this.setSizeX(this.image.getImageWidth());
        this.setSizeY(this.image.getImageHeight());
    }
    
    public Sprite(float xPos, float yPos, float sizeX, float sizeY, Texture t) {
        super(xPos, yPos, sizeX, sizeY);
        this.image = t;
    }
    
    public Sprite(float xPos, float yPos, float sizeX, float sizeY, String filetype, String path) {
        super(xPos, yPos, sizeX, sizeY);
        try {
            this.image = TextureLoader.getTexture(filetype, ResourceLoader.getResourceAsStream(path), GL11.GL_NEAREST);            
        } catch (IOException e) {
            System.err.println("Trouble loading texture-assets!!!");
            e.printStackTrace();
        }
    }
    
    public boolean loadTexture(String filetype, String path) {
        try {
            this.image = TextureLoader.getTexture(filetype, ResourceLoader.getResourceAsStream(path), GL11.GL_NEAREST);  
        } catch (IOException e) {
            System.err.println("Trouble loading texture-assets!!!");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public Texture getTexture() {
        return this.image;
    }
    
    public void setShader(Shader shader) {
        this.shader = shader;
    }
    
    @Override
    public void draw() {
        if (this.shader != null) this.shader.useShader();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        this.image.bind();
        GL11.glColor3f(1, 1, 1);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0, this.image.getHeight());
        GL11.glVertex2f(this.xPos, this.yPos);
        GL11.glTexCoord2f(this.image.getWidth(), this.image.getHeight());
        GL11.glVertex2f(this.xPos + this.sizeX, this.yPos);
        GL11.glTexCoord2f(this.image.getWidth(), 0);
        GL11.glVertex2f(this.xPos + this.sizeX, this.yPos + this.sizeY);
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f(this.xPos, this.yPos + this.sizeY);
        GL11.glEnd();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        if (this.shader != null) this.shader.detachShader();
    }
    
    /*
    public void replaceMagicColor() {
        //GL11.gl
    }
    */
    public void releaseTexture() {
        image.release();
    }
}
