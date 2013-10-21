package questions;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author WaltherKammen
 */
@XmlRootElement(name = "Answer")
@XmlType(propOrder = {"answerText", "score"})
public class Answer {

    private String answerText;
    private int score;

    
    public Answer() {
        
    }
    
    public Answer(String answerText, int score) {
        this.answerText = answerText;
        this.score = score;
    }
    
    /**
     *
     * @param answerText
     */
    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    /**
     *
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     *
     * @return
     */
    @XmlAttribute(name="answeText")
    public String getAnswerText() {
        return answerText;
    }

    /**
     *
     * @return
     */
    @XmlAttribute(name="score")
    public int getScore() {
        return score;
    }
    
    public String toString() {
        return score + " " + answerText;
    }
}
