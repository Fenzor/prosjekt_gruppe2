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

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getAnswerText() {
        return answerText;
    }

    public int getScore() {
        return score;
    }
}
