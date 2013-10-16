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
@XmlType(propOrder = {"text", "answerList"})
public class Question {

    private String imageFilePath;
    private String text;
    private List<Answer> answerList;

    /**
     *
     */
    public Question() {
        imageFilePath = "";
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
     * @param imageFilePath the filepath to the image
     */
    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }
    
    /**
     *
     * @return
     */
    public String getText() {
        return text;
    }

    /**
     *
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

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
}
