/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xml;

import game.Employee;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author WaltherKammen
 */
public class Test2 {
    public static void main(String[] args) {
        try {
            ArrayList<Employee> list = (ArrayList<Employee>) XMLReader.getEmployees();
            
            for(Employee e:list) {
                System.out.println(e.toString());
            }
            
        } catch (Exception ex) {
            Logger.getLogger(Test2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
