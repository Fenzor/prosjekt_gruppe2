/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import javax.swing.JOptionPane;
import org.lwjgl.*;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.PNGDecoder;
import sound.MusicLibrary;
import sound.SoundLibrary;
/**
 *
 * @author Lars Aksel
 */
public class Client implements Runnable {
    private int widthWindow = 1280;
    private int heightWindow = 720;
    private boolean fullscreen = false;
    private GameHandle gHandle;
    private Thread clientThread;
    private boolean isRunning;
    
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
        Window win = new Window(widthWindow, heightWindow, fullscreen);
        MusicLibrary mLib = new MusicLibrary();
        mLib.init();
        SoundLibrary sLib = new SoundLibrary();
        gHandle = new GameHandle(win, mLib, sLib);
                
        initGL(); // init OpenGL
        MenuWindow menu = new MenuWindow(gHandle);
        GameWindow gw = new GameWindow(gHandle);
        while (isRunning) {
            switch (menu.run()) {
                case NEWGAME: 
                    
                    if (gw.run() == Choices.EXIT) isRunning = false;
                    break;
                case LOADGAME:
                    // loading game...
                    //running = false;
                    break;
                case SAVEGAME:
                    // saving game...
                    break;
                case EXIT:
                    isRunning = false;
                    break;
                default:
                    break;
            }
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
     * Sets librarypath for native-compiled library-files...
     * Is used for LWJGL-library
     */
    private void setNatives() {
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
            System.exit(1);
        }
    }
    
    // Used to convert .png icon file unto bytebuffer
    /*
    private static ByteBuffer loadIcon(URL url) throws IOException {
        InputStream is = url.openStream();
        try {
            PNGDecoder decoder = new PNGDecoder(is);
            ByteBuffer bb = ByteBuffer.allocateDirect(decoder.getWidth()*decoder.getHeight()*4);
            decoder.decode(bb, decoder.getWidth()*4, PNGDecoder.Format.RGBA);
            bb.flip();
            return bb;
        } finally {
            is.close();
        }
    }
  */
    
    /*
     * Method called when exiting...
     */
    private void destroy() {
        gHandle.getMusicLib().destroy();
        AL.destroy();
        Display.destroy();
    }
    
    /*
     * Main-method...
     */
    public static void main(String... args) {
        Client k = new Client();        
        k.setNatives();
        k.runGame();
    }
}
