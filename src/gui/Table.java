/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import graphics.Sprite;
import game.Employee;
import java.util.ArrayList;

/**
 *
 * @author Lars Aksel
 */
public class Table extends Sprite {
    private ArrayList<Employee> emp;
    
    public Table(int xPos, int yPos, int sizeX, int sizeY) {
        super(xPos, yPos, sizeX, sizeY);
        emp = new ArrayList<>();
    }
    
    public void addEmployee() {
        
    }
}
