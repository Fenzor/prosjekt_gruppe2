/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sound;

import java.io.IOException;
import java.util.ArrayList;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.SoundStore;

/**
 *
 * @author Lars Aksel
 */
public class MusicLibrary implements Runnable {
    private ArrayList<Music> musicLib;
    private boolean isRunning;
    private boolean isPlaying;
    private Thread musicThread;
    
    public MusicLibrary() {
        this.musicThread = new Thread(this, "Music_Thread");
        this.musicLib = new ArrayList();
        this.isRunning = false;
    }
    
    public synchronized void init() {
        if (!this.isRunning) {
            this.isRunning = true;
            this.musicThread.start();
        }
    }
    
    private void loadAll() {
        try {
            musicLib.add(new Music("res/bgm/ambient.ogg", true));
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
    
    public synchronized int load(String ref, boolean streamingHint) {
        try {
            musicLib.add(new Music(ref, streamingHint));
        } catch (SlickException e) {
            e.printStackTrace();
        }
        return musicLib.size() - 1;
    }
    
    public synchronized Music getMusic(int index) {
        return this.musicLib.get(index);
    }
    
    public void destroy() {
        this.isRunning = false;
    }
    
    @Override
    public void run() {
        this.loadAll();
        while (isRunning) {
            //if (isPlaying) {
                SoundStore.get().poll(0);
            //}            
            try {
                Thread.sleep(400); // Sleeps for 400 ms...
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }        
    }
}
