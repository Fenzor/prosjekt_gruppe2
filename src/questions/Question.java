package questions;

import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author WaltherKammen
 */
@XmlRootElement(name = "Question")
@XmlType(propOrder = {"id", "imageFilePath", "text", "answerList"})
public class Question {

    private int id;
    private String imageFilePath;
    private String text;
    private List<Answer> answerList;
    private String answer;
    private boolean correct;

    public Question() {
        imageFilePath = "";
    }
    
    public Question(int id, String imageFilePath, String text, List<Answer> answerList) {
        this.id = id;
        this.imageFilePath = imageFilePath;
        this.text = text;
        this.answerList = answerList;
    }
    
    public Question(String text, String answer, boolean correct){
        this.text = text;
        this.answer = answer;
        this.correct = correct;
    }
    
    @XmlAttribute(name="id")
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getText(){
        return text;
    }
    
    public void setText(String newText){
        this.text = newText;
    }
    
    public String getAnswer(){
        return answer;
    }
    
    public void setAnswer(String newAnswer){
        this.answer = newAnswer;
    }
    
    public boolean getCorrect(){
        return correct;
    }
    
    public void setCorrect(boolean newCorrect){
        this.correct = newCorrect;
    }
    
    /**
     *
     * @return 
     */
    @XmlAttribute(name="imageFilePath")
    public String getImageFilePath() {
        return imageFilePath;
    }

    /**
     *
     * @param imageFilePath filsi til eventuelt bilde
     */
    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }
    
    /**
     *
     * @return
     */
//    public String getText() {
//        return text;
//    }

    /**
     *
     * @param text
     */
//    public void setText(String text) {
//        this.text = text;
//    }

    /**
     *
     * @return
     */
    @XmlElementWrapper(name = "answerList")
    @XmlElement(name = "answer")
    public List<Answer> getAnswerList() {
        return answerList;
    }

    /**
     *
     * @param answerList
     */
    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }
    
    @Override
    public String toString() {
        String t = text;
        for (Answer a:answerList) {
            t += "\n\t"+a.toString();
        }
        return t;
    }
}
