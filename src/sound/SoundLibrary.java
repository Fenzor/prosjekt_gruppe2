/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sound;

import java.util.ArrayList;
import org.newdawn.slick.Sound;

/**
 *
 * @author LarsAksel
 */
public class SoundLibrary {
    private ArrayList<Sound> sounds;
    
    public SoundLibrary() {
        sounds = new ArrayList();
    }
    
    public Sound getSound(int index) {
        return this.sounds.get(index);
    }
}
