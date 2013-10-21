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

    private final int widthWindow = 1280;
    private final int heightWindow = 720;
    private final Thread clientThread;
    private boolean fullscreen = false;
    private boolean isClientRunning;
    private boolean isGameRunning;
    private MusicLibrary mLib;
    private InputHandler input;
    private Window currentWindow;
    private Window menuWindow;
    private Window gameWindow;
    private Button newGame;
    private Button loadGame;
    private Button quitGame;
    private final String gameTitle = "Awsome Dev Tycoon v2.0";
    
    /**
     * time at last frame
     */
    long lastFrame;

    /**
     * frames per second
     */
    double fps;
    /**
     * last fps time
     */
    long lastFPS;

    public Client() {
        this.clientThread = new Thread(this, "Game_Thread");
    }

    /*
     * Init-method to be called when starting the game, and initiating the client-thread...
     */
    public void runGame() {
        this.isClientRunning = true;
        this.clientThread.start();
    }

    /*
     * Main client-thread...
     * This method should NOT be called directly, but only through Thread.start()
     */
    @Override
    public void run() {
        try {
            Display.setDisplayMode(new DisplayMode(widthWindow, heightWindow));
            Display.setTitle(gameTitle);
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        initGL(); // init OpenGL
        initMainMenu(); // init main menu
        input = new InputHandler(menuWindow);
        input.init();
        mLib = new MusicLibrary();
        mLib.init();

        getDelta(); // call once before loop to initialise lastFrame
        lastFPS = getTime(); // call before loop to initialise fps timer

        
        
        while (isClientRunning) {
            int delta = getDelta();
            updateFPS();
            currentWindow.drawAll();
            checkGlobalInput();
            Button b = input.getButtonPressed();
            if (b != null) {
                if (b.equals(newGame)) {
                    isGameRunning = true;
                    
                    gameWindow = new Window();
                    this.switchCurrentWindow(gameWindow);
                    int bgLayer01 = gameWindow.addLayer();
                    int carLayer = gameWindow.addLayer();
                    int bgLayer02 = gameWindow.addLayer();
                    
                    Sprite bg01 = new Sprite(0, 0, widthWindow, heightWindow, "png", "res/images/street.png");
                    Sprite bg02 = new Sprite(0, 0, widthWindow, heightWindow, "png", "res/images/plainOffice.png");
                    
                    TextType menuText = new TextType("res/font/clacon.ttf", 45f, true, new Color(1.0f, 0, 0.0f, 0), TrueTypeFont.ALIGN_CENTER);
                    Button menuButton = new Button(widthWindow - 260, heightWindow - 70, 170, 50, menuText, "Meny");
                    menuButton.loadAllStates("png", "res/images/menuButtonDefault.png");
                    gameWindow.addButtonToLayer(menuButton);
                    
                    ColorPicker cp = new ColorPicker(new Color(1.0f, 0, 1.0f), new Color(0.1f, 0.1f, 0.7f, 0.2f));
                    bg02.setShader(cp);
                    
                    gameWindow.addSpriteToLayer(bgLayer01, bg01);
                    gameWindow.addSpriteToLayer(bgLayer02, bg02);
                    
                    Window menuOverlayWin = new Window();
                    int menuOverlay = menuOverlayWin.addLayer();
                    int sizeX = 650;
                    int sizeY = 550;
                    
                    Sprite overlay = new Sprite(widthWindow/2 - sizeX/2, heightWindow/2 - sizeY/2, sizeX, sizeY, "png", "res/images/menuOverlay.png");
                    menuOverlayWin.addSpriteToLayer(menuOverlay, overlay);
                    
                    int buttonSizeX = 300;
                    int buttonSizeY = 60;
                    int hX = heightWindow/2 + 125;
                    Button overlayButtonMainMenu = new Button(widthWindow/2 - buttonSizeX/2, hX, buttonSizeX, buttonSizeY, menuText, "Main Menu");
                    overlayButtonMainMenu.loadAllStates("png", "res/images/menuButtonDefault.png");
                    menuOverlayWin.addButtonToLayer(overlayButtonMainMenu);
                    Button overlayButtonSaveGame = new Button(widthWindow/2 - buttonSizeX/2, hX - (buttonSizeY + 20), buttonSizeX, buttonSizeY, menuText, "Save Game");
                    overlayButtonSaveGame.loadAllStates("png", "res/images/menuButtonDefault.png");
                    menuOverlayWin.addButtonToLayer(overlayButtonSaveGame);
                    Button overlayButtonBack = new Button(widthWindow/2 - buttonSizeX/2, hX - 2*(buttonSizeY + 20), buttonSizeX, buttonSizeY, menuText, "Back");
                    overlayButtonBack.loadAllStates("png", "res/images/menuButtonDefault.png");
                    menuOverlayWin.addButtonToLayer(overlayButtonBack);
                    Button overlayButtonExit = new Button(widthWindow/2 - buttonSizeX/2, hX - 3*(buttonSizeY + 20), buttonSizeX, buttonSizeY, menuText, "Exit to Windows");
                    overlayButtonExit.loadAllStates("png", "res/images/menuButtonDefault.png");
                    menuOverlayWin.addButtonToLayer(overlayButtonExit);
                    
                    while (isGameRunning && isClientRunning) {
                        
                        Button b2 = input.getButtonPressed();
                        if (b2 != null) {
                            if (b2.equals(menuButton)) {
                                Sprite transparent = new Sprite(0, 0, widthWindow, heightWindow, "png", "res/images/redDot.png");
                                ColorPicker cp2 = new ColorPicker(new Color(1.0f, 0f, 0f), new Color(0f, 0f, 0f, 0.5f));
                                transparent.setShader(cp2);
                                while (isClientRunning) {
                                    gameWindow.drawAll();
                                    transparent.draw();
                                    this.switchCurrentWindow(menuOverlayWin);
                                    b2 = input.getButtonPressed();
                                    if (b2 != null) {
                                        if (b2.equals(overlayButtonBack)) {
                                            this.switchCurrentWindow(gameWindow);
                                            break;
                                        }
                                        if (b2.equals(overlayButtonExit)) {
                                            this.isClientRunning = false;
                                            break;
                                        }
                                    }
                                    currentWindow.drawAll();
                                    updateFPS();
                                    checkGlobalInput();
                                    Display.sync(60);
                                    Display.update();
                                }
                            }
                        }
                        
                        currentWindow.drawAll();
                        updateFPS();
                        checkGlobalInput();
                        Display.sync(60);
                        Display.update();
                    }
                } else if (b.equals(loadGame)) {
                    
                } else if (b.equals(quitGame)) {
                    isClientRunning = false;
                }
            }
            
            checkGlobalInput();
            Display.sync(60);
            Display.update();
        }
        this.destroy();
    }

    /*
     * Used to initiate the Main menu with default values...
     */
    public void initMainMenu() {
        // Add background...
        menuWindow = new Window(); // The window being drawn to...
        int layer01 = menuWindow.addLayer();
        Sprite menuBackground = new Sprite(0, 0, this.widthWindow, this.heightWindow);
        menuBackground.loadTexture("png", "res/images/startScreen.png");
        menuWindow.addSpriteToLayer(layer01, menuBackground);

        // Generate buttons...
        int buttonWidth = 300;
        int buttonHeight = 70;
        TextType menuText = new TextType("res/font/clacon.ttf", 55, true, new Color(1.0f, 0, 0.0f, 0), TrueTypeFont.ALIGN_CENTER);
        newGame = new Button(this.getWindowWidth() / 2 - buttonWidth / 2, this.getWindowHeight() / 2 + this.getWindowHeight() / 4, buttonWidth, buttonHeight, menuText, "New Game");
        loadGame = new Button(this.getWindowWidth() / 2 - buttonWidth / 2, this.getWindowHeight() / 2 + this.getWindowHeight() / 8, buttonWidth, buttonHeight, menuText, "Load Game");
        quitGame = new Button(this.getWindowWidth() / 2 - buttonWidth / 2, this.getWindowHeight() / 2, buttonWidth, buttonHeight, menuText, "Quit Game");
        newGame.loadDefaultButtonState("png", "res/images/menuButtonDefault.png");
        newGame.loadHoveredButtonState("png", "res/images/menuButtonHovered.png");
        newGame.loadClickedButtonState("png", "res/images/menuButtonClicked.png");
        loadGame.loadDefaultButtonState("png", "res/images/menuButtonDefault.png");
        loadGame.loadHoveredButtonState("png", "res/images/menuButtonHovered.png");
        loadGame.loadClickedButtonState("png", "res/images/menuButtonClicked.png");
        quitGame.loadDefaultButtonState("png", "res/images/menuButtonDefault.png");
        quitGame.loadHoveredButtonState("png", "res/images/menuButtonHovered.png");
        quitGame.loadClickedButtonState("png", "res/images/menuButtonClicked.png");

        // Put buttons in the dynamic layer...
        menuWindow.addButtonToLayer(newGame);
        menuWindow.addButtonToLayer(loadGame);
        menuWindow.addButtonToLayer(quitGame);
        currentWindow = menuWindow;
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
    
    private void switchCurrentWindow(Window win) {
        this.currentWindow = win;
        this.input.setWindow(win);
    }

    /*
     * Is used to check for global input, this may be moved...
     */
    protected void checkGlobalInput() {
        if (Keyboard.isKeyDown(Keyboard.KEY_F11)) {
            this.fullscreen = !this.fullscreen;
            this.setDisplayMode(this.widthWindow, this.heightWindow, this.fullscreen);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) || Display.isCloseRequested()) {
            this.isClientRunning = false;
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
        if ((Display.getDisplayMode().getWidth() == width)
                && (Display.getDisplayMode().getHeight() == height)
                && (Display.isFullscreen() == fullscreen)) {
            return;
        }

        try {
            DisplayMode targetDisplayMode = null;

            if (fullscreen) {
                DisplayMode[] modes = Display.getAvailableDisplayModes();
                int freq = 0;

                for (int i = 0; i < modes.length; i++) {
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
                        if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel())
                                && (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
                            targetDisplayMode = current;
                            break;
                        }
                    }
                }
            } else {
                targetDisplayMode = new DisplayMode(width, height);
            }

            if (targetDisplayMode == null) {
                System.out.println("Failed to find value mode: " + width + "x" + height + " fs=" + fullscreen);
                return;
            }

            Display.setDisplayMode(targetDisplayMode);
            Display.setFullscreen(fullscreen);

        } catch (LWJGLException e) {
            System.out.println("Unable to setup mode " + width + "x" + height + " fullscreen=" + fullscreen + e);
        }
    }

    /**
     * Calculate how many milliseconds have passed since last frame.
     *
     * @return milliseconds passed since last frame
     */
    public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;

        return delta;
    }

    /**
     * Get the accurate system time
     *
     * @return The system time in milliseconds
     */
    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    /**
     * Calculate the FPS and set it in the title bar
     */
    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle(gameTitle + " ,FPS: " + fps);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
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
        input.destroy();
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
