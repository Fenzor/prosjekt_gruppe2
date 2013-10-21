/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.swing;

import javax.swing.JOptionPane;
import questions.Question;

/**
 *
 * @author WaltherKammen
 */
public class SwingTest {
    public static void main(String[] args) throws Exception {
        Question q = xml.XMLReader.getQuestions().get(0);
        QuestionDialog qd = new QuestionDialog(q, null, true);
        boolean b = qd.showWindow();
        JOptionPane.showMessageDialog(null, b);
        
    }
}
