/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.ArrayList;

/**
 *
 * @author Dahl
 */
public class Test2 {

    public static void main(String[] args) {
        Player player = new Player();
        Company company = player.loadGame();

        System.out.println(company.toString());
    }
}
