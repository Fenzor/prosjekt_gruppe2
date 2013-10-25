/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Lars Aksel
 */
public class SaveLoad {
    
    public static boolean saveGame(World world, String filepath) {
        try {
            // Åpner en fil som skrive til
            FileOutputStream saveFile = new FileOutputStream(filepath + ".sav");
            try (ObjectOutputStream save = new ObjectOutputStream(saveFile)) {
                save.writeObject(world);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static World loadGame(String filepath) {
        World w = null;
        try {
            //Åpner filen som skal leses fra
            FileInputStream saveFile = new FileInputStream(filepath);
            //Lager en ObjectInputStream til å hente objekter fra den lagrede filen.
            ObjectInputStream save = new ObjectInputStream(saveFile);
            w = (World) save.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return w;
    }
}
