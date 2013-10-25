/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.swing;

import game.Employee;
import java.util.List;
import javax.swing.JOptionPane;
import questions.Question;

/**
 *
 * @author WaltherKammen
 */
public class SwingTest {
    public static void main(String[] args) throws Exception {
        test2();
    }
    
    private static void test1() throws Exception {
        Question q = xml.XMLReader.getQuestions().get(2);
        QuestionDialog qd = new QuestionDialog(q, new javax.swing.JFrame(), true);
        boolean b = qd.showWindow();
        JOptionPane.showMessageDialog(null, b);
    }
    
    public static void test2() throws Exception {
        List<Employee> eList = xml.XMLReader.getEmployees();
        EmployeeDialog ed = new EmployeeDialog(eList, new javax.swing.JFrame(), true);
        ed.setVisible(true);
    }
}
