/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import game.Employee;
import game.World;
import gui.swing.EmployeeDialog;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.lwjgl.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import sound.MusicLibrary;

/**
 *
 * @author Lars Aksel
 */
public class Client implements Runnable {

    private int widthWindow = 1280;
    private int heightWindow = 720;
    private int maxFps = 120;
    private final Thread clientThread;
    private boolean fullscreen = false;
    private boolean isClientRunning;
    private boolean isGameRunning;
    private MusicLibrary mLib;
    private InputHandler input;
    private Window currentWindow;
    private Window menuWindow;
    private Window gameWindow;
    private Window menuOverlayWin;
    private Button newGame;
    private Button loadGame;
    private Button quitGame;
    private CarPool cars;
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
        int faktor = 100;
        this.heightWindow = 9 * faktor;
        this.widthWindow = 16 * faktor;
    }

    /*
     * Init-method to be called when starting the game, and initiating the client-thread...
     */
    public void init() {
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
        createMenu(); // init main menu
        input = new InputHandler(menuWindow);
        input.init();
        mLib = new MusicLibrary();
        mLib.init();

        getDelta(); // call once before loop to initialise lastFrame
        lastFPS = getTime(); // call before loop to initialise fps timer
        
        Display.setVSyncEnabled(true);

        while (isClientRunning) {
            //int delta = getDelta();
            updateFPS();
            currentWindow.drawAll();
            checkGlobalInput();
            Button b = input.getButtonPressed();
            if (b != null) {
                if (b.equals(newGame)) {
                    runNewGame();
                } else if (b.equals(loadGame)) {
                    //JOptionPane.showMessageDialog(null, "\"Load game\" NOT implemented!", "Warning", JOptionPane.WARNING_MESSAGE);
                    JFileChooser fc = new JFileChooser();
                    fc.showOpenDialog(fc);
                    String s = fc.getSelectedFile().toString();
                    System.out.println(s);
                } else if (b.equals(quitGame)) {
                    isClientRunning = false;
                }
            }
            update();
        }
        this.destroy();
    }

    /*
     * Used to initiate the Main menu with default values...
     */
    public void createMenu() {
        // Add background...
        menuWindow = new Window(); // The window being drawn to...
        int layer01 = menuWindow.addLayer();
        int faktor = 11;
        Sprite menuBackground = new Sprite(
                this.widthWindow / 2 - (106 * faktor) / 2,
                this.heightWindow / 2 - (73 * faktor) / 2,
                106 * faktor,
                73 * faktor);
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
     * Starts new game..
     */
    public void runNewGame() {
        isGameRunning = true;
        
        float speed = 0.3f;
        float x = 200, y = 120;
        Car[] carCollection = {
            new Car(500, heightWindow - 1, x, y, speed, "png", "res/images/car01.png", new Vector2f(-2, -1)),
            new Car(500, heightWindow - 1 ,x, y, speed, "png", "res/images/car02.png", new Vector2f(-2, -1)),
            new Car(500, heightWindow - 1, x, y, speed, "png", "res/images/car03.png", new Vector2f(-2, -1)),
            new Car(-x+1, heightWindow/2 - 120 , x, y, speed, "png", "res/images/car01.png", new Vector2f(2, 1)),
            new Car(-x+1, heightWindow/2 - 120 ,x, y, speed, "png", "res/images/car02.png", new Vector2f(2, 1)),
            new Car(-x+1, heightWindow/2 - 120 , x, y, speed, "png", "res/images/car03.png", new Vector2f(2, 1))
        };
        
        cars = new CarPool(widthWindow, heightWindow, 500, 3000, carCollection);
        cars.init();
        
        gameWindow = new Window();
        this.switchCurrentWindow(gameWindow);
        int bgLayer01 = gameWindow.addLayer();
        int carLayer = gameWindow.addLayer(cars);
        int bgLayer02 = gameWindow.addLayer();
        int bgLayer03 = gameWindow.addLayer();

        Sprite bg01 = new Sprite(0, 0, widthWindow, heightWindow, "png", "res/images/street.png");
        Sprite bg02 = new Sprite(0, 0, widthWindow, heightWindow, "png", "res/images/plainOffice.png");

        Sprite textField = new Sprite(10, 10, 350, this.heightWindow - 2 * 10, "png", "res/images/redDot.png");

        TextType menuText = new TextType("res/font/clacon.ttf", 45f, true, new Color(1.0f, 0, 0.0f, 0), TrueTypeFont.ALIGN_CENTER);
        TextType textFieldType = new TextType("res/font/clacon.ttf", 40f, true, new Color(0.0f, 0, 0.0f, 0), TrueTypeFont.ALIGN_UPPER_LEFT);
        String text = "Employees: ";
        TextField tf = new TextField(10, this.heightWindow - 10, text, textFieldType);
        this.gameWindow.addSpriteToLayer(bgLayer03, tf);

        Button menuButton = new Button(widthWindow - 260, heightWindow - 70, 170, 50, menuText, "Menu");
        menuButton.loadAllStates("png", "res/images/menuButtonDefault.png");
        gameWindow.addButtonToLayer(menuButton);

        Button employeeButton = new Button(textField.getSizeX() - 35, heightWindow - (12 + 30), 30, 30);
        employeeButton.loadAllStates("png", "res/images/menuButtonDefault.png");
        gameWindow.addButtonToLayer(employeeButton);

        ColorPicker cp = new ColorPicker(new Color(1.0f, 0, 1.0f), new Color(0.1f, 0.1f, 0.7f, 0.2f));
        bg02.setShader(cp);

        ColorPicker cp2 = new ColorPicker(new Color(1.0f, 0.0f, 0.0f), new Color(1f, 1f, 1f, 0.6f));
        textField.setShader(cp2);

        gameWindow.addSpriteToLayer(bgLayer01, bg01);
        gameWindow.addSpriteToLayer(bgLayer02, bg02);
        gameWindow.addSpriteToLayer(bgLayer02, textField);

        menuOverlayWin = new Window();
        int menuOverlay = menuOverlayWin.addLayer();
        int sizeX = 650;
        int sizeY = 550;

        Sprite overlay = new Sprite(widthWindow / 2 - sizeX / 2, heightWindow / 2 - sizeY / 2, sizeX, sizeY, "png", "res/images/menuOverlay.png");
        menuOverlayWin.addSpriteToLayer(menuOverlay, overlay);
        
        
        
        while (isGameRunning && isClientRunning) {

            Button b2 = input.getButtonPressed();
            if (b2 != null) {
                if (b2.equals(menuButton)) {
                    runOverlayMenu();
                }
                if (b2.equals(employeeButton)) {
                    Runnable r = new Runnable() {
                        public void run() {
                            try {
                                List<Employee> eList = xml.XMLReader.getEmployees();
                                EmployeeDialog ed = new EmployeeDialog(eList, null, true);
                                ed.setVisible(true);
                            } catch (Exception e) {

                            }
                        }
                    };
                    Thread t = new Thread(r);
                    t.start();
                }
            }
            currentWindow.drawAll();
            update();
        }
    }

    private void runOverlayMenu() {
        int buttonSizeX = 310;
        int buttonSizeY = 60;
        int hX = heightWindow / 2 + 125;

        TextType menuText = new TextType("res/font/clacon.ttf", 45f, true, new Color(1.0f, 0, 0.0f, 0), TrueTypeFont.ALIGN_CENTER);
        Button overlayButtonMainMenu = new Button(widthWindow / 2 - buttonSizeX / 2, hX, buttonSizeX, buttonSizeY, menuText, "Options");
        overlayButtonMainMenu.loadAllStates("png", "res/images/menuButtonDefault.png");
        menuOverlayWin.addButtonToLayer(overlayButtonMainMenu);
        Button overlayButtonSaveGame = new Button(widthWindow / 2 - buttonSizeX / 2, hX - (buttonSizeY + 20), buttonSizeX, buttonSizeY, menuText, "Save Game");
        overlayButtonSaveGame.loadAllStates("png", "res/images/menuButtonDefault.png");
        menuOverlayWin.addButtonToLayer(overlayButtonSaveGame);
        Button overlayButtonBackToMainMenu = new Button(widthWindow / 2 - buttonSizeX / 2, hX - 2 * (buttonSizeY + 20), buttonSizeX, buttonSizeY, menuText, "Exit to Main Menu");
        overlayButtonBackToMainMenu.loadAllStates("png", "res/images/menuButtonDefault.png");
        menuOverlayWin.addButtonToLayer(overlayButtonBackToMainMenu);
        Button overlayButtonExitToWindows = new Button(widthWindow / 2 - buttonSizeX / 2, hX - 3 * (buttonSizeY + 20), buttonSizeX, buttonSizeY, menuText, "Exit to OS");
        overlayButtonExitToWindows.loadAllStates("png", "res/images/menuButtonDefault.png");
        menuOverlayWin.addButtonToLayer(overlayButtonExitToWindows);
        Button overlayButtonBackToGame = new Button(widthWindow - 430, heightWindow - 190, 30, 30);
        overlayButtonBackToGame.loadAllStates("png", "res/images/exitButton.png");
        menuOverlayWin.addButtonToLayer(overlayButtonBackToGame);

        Sprite transparent = new Sprite(0, 0, widthWindow, heightWindow, "png", "res/images/redDot.png");
        ColorPicker cp2 = new ColorPicker(new Color(1.0f, 0f, 0f), new Color(0f, 0f, 0f, 0.5f));
        transparent.setShader(cp2);
        while (isClientRunning && isGameRunning) {
            gameWindow.drawAll();
            transparent.draw();
            this.switchCurrentWindow(menuOverlayWin);
            Button b = input.getButtonPressed();
            if (b != null) {
                if (b.equals(overlayButtonBackToMainMenu)) {
                    int value = JOptionPane.showOptionDialog(null, "Are you sure you want to end this game?\n(Any progress after last save will be lost)", "Question", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                    if (value == JOptionPane.YES_OPTION) {
                        this.switchCurrentWindow(menuWindow);
                        this.isGameRunning = false;
                        return;
                    }
                }
                if (b.equals(overlayButtonSaveGame)) {
                    //JOptionPane.showMessageDialog(null, "\"Save game\" NOT implemented!", "Warning", JOptionPane.WARNING_MESSAGE);
                    JFileChooser fc = new JFileChooser();
                    fc.showSaveDialog(fc);
                    String s = fc.getSelectedFile().toString();
                    World world = new World("test", "test");
                    world.saveGame(s);
                    
                }
                if (b.equals(overlayButtonExitToWindows)) {
                    this.isClientRunning = false;
                    break;
                }
                if (b.equals(overlayButtonBackToGame)) {

                    this.switchCurrentWindow(gameWindow);
                    return;
                }
            }
            currentWindow.drawAll();
            update();
        }
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

    public void update() {
        updateFPS();
        checkGlobalInput();
        Display.sync(maxFps);
        Display.update();
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
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
        if (cars != null) cars.destroy();
    }

    /*
     * Main-method...
     */
    public static void main(String... args) {
        Client k = new Client();
        if (!k.setNatives()) {
            System.exit(1);
        }
        k.init();
    }
}
