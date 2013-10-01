/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import sound.MusicLibrary;
import sound.SoundLibrary;

/**
 *
 * @author Lars Aksel
 */
public class GameHandle {
    private MusicLibrary mLib;
    private SoundLibrary sLib;
    private Window win;
    
    public GameHandle(Window win, MusicLibrary mLib, SoundLibrary sLib) {
        this.win = win;
        this.mLib = mLib;
        this.sLib = sLib;
    }
    
    public Window getWindow() {
        return this.win;
    }
    
    public MusicLibrary getMusicLib() {
        return this.mLib;
    }
    
    public SoundLibrary getSoundLib() {
        return this.sLib;
    }
    
    public void setWindow(Window win) {
        this.win = win;
    }
    
    public void setMusicLib(MusicLibrary mLib) {
        this.mLib = mLib;
    }
    
    public void setSoundLib(SoundLibrary sLib) {
        this.sLib = sLib;
    }
}
