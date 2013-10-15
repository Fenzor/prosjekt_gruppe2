/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javax.swing.JOptionPane;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author Lars Aksel
 */
public class Menu extends Window {
    private Sprite background;
    private Button newGame;
    private Button loadGame;
    private Button quitGame;
    private GameHandle gHandle;
    
    public Menu(GameHandle gHandle) {
        super(gHandle.getWindow());
        this.gHandle = gHandle;
        this.background = new Sprite(0, 0, gHandle.getWindow().getWindowWidth(), gHandle.getWindow().getWindowHeight());
        this.background.loadTexture("png", "res/images/startScreen.png");
        int buttonWidth = 300;
        int buttonHeight = 70;
        Text menuText = new Text("res/font/AeroviasBrasilNF.ttf", 55, true, org.newdawn.slick.Color.blue, Text.ALIGN_CENTER);
        this.newGame = new Button(this.getWindowWidth()/2 - buttonWidth/2, this.getWindowHeight()/2 + this.getWindowHeight()/4, buttonWidth, buttonHeight, menuText);
        this.loadGame = new Button(this.getWindowWidth()/2 - buttonWidth/2, this.getWindowHeight()/2 + this.getWindowHeight()/8, buttonWidth, buttonHeight, menuText);
        this.quitGame = new Button(this.getWindowWidth()/2 - buttonWidth/2, this.getWindowHeight()/2, buttonWidth, buttonHeight, menuText);
        this.newGame.loadTexture("png", "res/images/button1.png");
        this.loadGame.loadTexture("png", "res/images/button1.png");
        this.quitGame.loadTexture("png", "res/images/button1.png");
    }   
    
    public Choices run() {
        //while-løkke...
        this.gHandle.getMusicLib().getMusic(0).play();
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            // Clear the screen and depth buffer
	    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            
        
            drawGL();
            Choices choice = updateKeys();
            if (choice != Choices.DO_NOTHING) return choice;
            
            
            Display.update();
            Display.sync(60); // cap fps to 60fps
        }
        
        return Choices.EXIT;
    }
    
    private Choices updateKeys() {
        int x = Mouse.getX(), y = Mouse.getY();
        this.checkGlobalInput();
        
        if (Mouse.isButtonDown(0)) {
            if (newGame.isInside(x, y)) {
                System.out.println("New game!!!");
                this.gHandle.getMusicLib().getMusic(0).pause();
                this.gHandle.getMusicLib().getMusic(0).stop();
                return Choices.NEWGAME;
            }
            else if (loadGame.isInside(x, y)) {
                System.out.println("Load game!!!");
                this.gHandle.getMusicLib().getMusic(0).pause();
                this.gHandle.getMusicLib().getMusic(0).stop();
                JOptionPane.showMessageDialog(null, "\'Load game\' er ikke implementert...", "Advarsel!", JOptionPane.WARNING_MESSAGE);
                return Choices.EXIT;
            }
            else if (quitGame.isInside(x, y)) {
                System.out.println("Quit game!!!");
                //this.gHandle.getMusicLib().getMusic(0).stop();
                return Choices.EXIT;
            }
        }
        return Choices.DO_NOTHING;
    }
    
    private void drawGL() {
        background.draw();
        this.newGame.draw("New Game", (int) this.newGame.getSizeX()/2, 5);
        this.loadGame.draw("Load Game", (int) this.loadGame.getSizeX()/2, 5);
        this.quitGame.draw("Quit Game", (int) this.quitGame.getSizeX()/2, 5);
    }
}