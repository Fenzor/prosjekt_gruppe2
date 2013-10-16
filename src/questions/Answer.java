package questions;

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
    public String getAnswerText() {
        return answerText;
    }

    /**
     *
     * @return
     */
    public int getScore() {
        return score;
    }
}
