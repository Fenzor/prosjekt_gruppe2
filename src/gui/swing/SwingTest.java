/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.swing;

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
        System.out.println(b);
    }
}
