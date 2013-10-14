/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

/**
 *
 * @author Lars Aksel
 */
//TODO move is clicked checkings to Button.java
public class GameWindow extends Window {
    private ArrayList<Sprite> sprites;
    private Button menu;
    private boolean clicked = false;
    Text t = new Text("res/font/AeroviasBrasilNF.ttf", 48, false);
    ColorPicker cp;
    
    
    public GameWindow(GameHandle gHandle) {
        super(gHandle.getWindow());
        sprites = new ArrayList();
        sprites.add(new Sprite(0, 0, gHandle.getWindow().getWindowWidth(), gHandle.getWindow().getWindowHeight()));
        sprites.add(new Car(900, 800, "png", "res/images/car01.png"));
        sprites.get(1).scaleSize(2, 2);
        sprites.add(new Sprite(0, 0, gHandle.getWindow().getWindowWidth(), gHandle.getWindow().getWindowHeight()));
        
        sprites.get(0).loadTexture("png", "res/images/street.png");
        sprites.get(2).loadTexture("png", "res/images/plainOffice.png");
        menu = new Button(gHandle.getWindow().getWindowWidth() - 225, gHandle.getWindow().getWindowHeight() - 75, 175, 50);
        menu.loadDefaultButtonState("png", "res/images/menu.png");
        menu.loadHoveredButtonState("png", "res/images/menuHovered.png");
        menu.loadClickedButtonState("png", "res/images/menuClicked.png");
        sprites.add(menu);
    }
    
    public Choices run() {
        
        // Used to test shader-code... IS NOT FINISHED!!!
        // Color c = new Color
        cp = new ColorPicker(new Color(255, 0, 255, 255), new Color(0, 0, 100, 100));
        //cp.useShader();
        
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            // Clear the screen and depth buffer
	    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            
            renderGL();
            if (updateKeys() == Choices.MAINMENU) return Choices.MAINMENU;
            
            Display.update();
            Display.sync(60); // cap fps to 60fps
        }
        return Choices.EXIT;
    }
    
    private Choices updateKeys() {        
        int x = Mouse.getX(), y = Mouse.getY();
        this.checkGlobalInput();
        
        // Testing boundries for MainMenu button...
        if (menu.isInside(x, y)) {
            menu.setHoveredState();
            if (Mouse.isButtonDown(0)) {
                menu.setClickedState();
                clicked = true;                
            }
            if (!Mouse.isButtonDown(0) && clicked) {
                return Choices.MAINMENU;
            }
            return Choices.DO_NOTHING;
        } else {
            menu.setDefaultState();
            clicked = false;
            return Choices.DO_NOTHING;
        }
    }
    
    /*
     * render method
     */
    private void renderGL() {
        // Textured sprites (i.e background and buttons)...
        for (int i = 0; i < sprites.size(); i++) {
            if (i == 2) {
                cp.useShader();
                sprites.get(i).draw();
                cp.detachShader();
                continue;
            }
            sprites.get(i).draw();
        } 
        
        
        
        // HUD-sheet on left side...
        int size = 10;
        float alpha = 0.50f;
        AABB hud = new AABB(size, size, 250, this.getWindowHeight() - size*2);
        GL11.glColor4f(1, 1, 1, alpha);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(hud.getX(), hud.getY());
        GL11.glVertex2f(hud.getX() + hud.getSizeX(), hud.getY());
        GL11.glVertex2f(hud.getX() + hud.getSizeX(), hud.getY() + hud.getSizeY());
        GL11.glVertex2f(hud.getX(), hud.getY() + hud.getSizeY());
        GL11.glEnd();
        GL11.glColor4f(1, 1, 1, 1);
        
        // Draw example-text...
        //t.draw(400, 450, "Hello World!", org.newdawn.slick.Color.magenta);
    }
}
