/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.File;
import javax.swing.JOptionPane;
import org.lwjgl.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import sound.MusicLibrary;
/**
 *
 * @author Lars Aksel
 */
public class Client implements Runnable {
    private int widthWindow = 1280;
    private int heightWindow = 720;
    private boolean fullscreen = false;
    //private GameHandle gHandle;
    private Thread clientThread;
    private boolean isRunning;
    private MusicLibrary mLib;
    
    public Client() {
        this.clientThread = new Thread(this, "Game_Thread");
    }
    
    /*
     * Init-method to be called when starting the game, and initiating the client-thread...
     */
    public void runGame() {
        this.isRunning = true;
        this.clientThread.start();
    }
    
    /*
     * This method should NOT be called directly, but only through Thread.start()
     */
    @Override
    public void run() {
        try {
            Display.setDisplayMode(new DisplayMode(widthWindow, heightWindow));
            Display.setTitle("Awesome Dev Tycoon v2.0");
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        
        initGL(); // init OpenGL
        
        Window currentWindow = new Window(); // The window being drawn to...
        int layer01 = currentWindow.addLayer();
        Sprite menuBackground = new Sprite(0, 0, this.widthWindow, this.heightWindow);
        menuBackground.loadTexture("png", "res/images/startScreen.png");
        currentWindow.addSpriteToLayer(layer01, menuBackground);
        
        int buttonWidth = 300;
        int buttonHeight = 70;
        Text menuText = new Text("res/font/clacon.ttf", 55, true, new Color(1.0f,0,0.0f,0), Text.ALIGN_CENTER);
        Button newGame = new Button(this.getWindowWidth()/2 - buttonWidth/2, this.getWindowHeight()/2 + this.getWindowHeight()/4, buttonWidth, buttonHeight, menuText, "New Game");
        Button loadGame = new Button(this.getWindowWidth()/2 - buttonWidth/2, this.getWindowHeight()/2 + this.getWindowHeight()/8, buttonWidth, buttonHeight, menuText, "Load Game");
        Button quitGame = new Button(this.getWindowWidth()/2 - buttonWidth/2, this.getWindowHeight()/2, buttonWidth, buttonHeight, menuText, "Quit Game");
        newGame.loadTexture("png", "res/images/button1.png");
        loadGame.loadTexture("png", "res/images/button1.png");
        quitGame.loadTexture("png", "res/images/button1.png");
        int dLayer01 = currentWindow.addDynamicLayer();
        currentWindow.addButtonToLayer(dLayer01, newGame);
        currentWindow.addButtonToLayer(dLayer01, loadGame);
        currentWindow.addButtonToLayer(dLayer01, quitGame);
        mLib = new MusicLibrary();
        mLib.init();
        //SoundLibrary sLib = new SoundLibrary();
        //gHandle = new GameHandle(win, mLib, sLib);
        
        
        
        //Menu menu = new Menu(gHandle);
        //GameWindow gw = new GameWindow(gHandle);
        while (isRunning) {
            currentWindow.drawAll();
            checkGlobalInput();
            
            Display.sync(60);
            Display.update();
        }
        this.destroy();
    }
       
    /*
     * Initiate OpenGL states...
     */
    private void initGL() {
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        
        // enable alpha blending
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA); 
                
        // Set perspective to orthogonal...
        GL11.glMatrixMode(GL11.GL_PROJECTION);
	GL11.glLoadIdentity();
	GL11.glOrtho(0, widthWindow, 0, heightWindow, 1, -1);
	GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }
    
    /*
     * Returns width of window...
     */
    public int getWindowWidth() {
        return this.widthWindow;
    }

    /*
     * Returns height of window...
     */
    public int getWindowHeight() {
        return this.heightWindow;
    }
    
    /*
     * Is used to check for global input, this may be moved...
     */    
    protected void checkGlobalInput() {
        if (Keyboard.isKeyDown(Keyboard.KEY_F11)) {
            this.fullscreen = !this.fullscreen;
            this.setDisplayMode(this.heightWindow, this.widthWindow, this.fullscreen);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            this.isRunning = false;
        }
    }
        
    /**
    * Set the display mode to be used 
    * 
    * @param width The width of the display required
    * @param height The height of the display required
    * @param fullscreen True if we want fullscreen mode
    */
   public void setDisplayMode(int width, int height, boolean fullscreen) {

       // return if requested DisplayMode is already set
       if ((Display.getDisplayMode().getWidth() == width) && 
           (Display.getDisplayMode().getHeight() == height) && 
           (Display.isFullscreen() == fullscreen)) {
               return;
       }

       try {
           DisplayMode targetDisplayMode = null;

           if (fullscreen) {
               DisplayMode[] modes = Display.getAvailableDisplayModes();
               int freq = 0;

               for (int i=0;i<modes.length;i++) {
                   DisplayMode current = modes[i];

                   if ((current.getWidth() == width) && (current.getHeight() == height)) {
                       if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
                           if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
                               targetDisplayMode = current;
                               freq = targetDisplayMode.getFrequency();
                           }
                       }

                       // if we've found a match for bpp and frequence against the 
                       // original display mode then it's probably best to go for this one
                       // since it's most likely compatible with the monitor
                       if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) &&
                           (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
                               targetDisplayMode = current;
                               break;
                       }
                   }
               }
           } else {
               targetDisplayMode = new DisplayMode(width,height);
           }

           if (targetDisplayMode == null) {
               System.out.println("Failed to find value mode: "+width+"x"+height+" fs="+fullscreen);
               return;
           }

           Display.setDisplayMode(targetDisplayMode);
           Display.setFullscreen(fullscreen);

       } catch (LWJGLException e) {
           System.out.println("Unable to setup mode "+width+"x"+height+" fullscreen="+fullscreen + e);
       }
   }
    
    /*
     * Sets librarypath for native-compiled library-files...
     * Is used for LWJGL-library
     */
    private boolean setNatives() {
        String OS = System.getProperty("os.name").toLowerCase();
        if (OS.indexOf("win") >= 0) {
            System.setProperty("org.lwjgl.librarypath", new File("lib//natives//windows").getAbsolutePath());
        } else if (OS.indexOf("mac") >= 0) {
            System.setProperty("org.lwjgl.librarypath", new File("lib//natives//macosx").getAbsolutePath());
        } else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") >= 0) {
            System.setProperty("org.lwjgl.librarypath", new File("lib//natives//linux").getAbsolutePath());
        } else {
            System.err.println("Your OS is not supported!");
            JOptionPane.showMessageDialog(null, "Your OS is not supported!", "Warning!", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    
    /*
     * Method called when exiting...
     */
    private void destroy() {
        mLib.destroy();
        AL.destroy();
        Display.destroy();
    }
    
    /*
     * Main-method...
     */
    public static void main(String... args) {
        Client k = new Client();        
        if (!k.setNatives()) {
            System.exit(1);
        }
        k.runGame();
    }
}
