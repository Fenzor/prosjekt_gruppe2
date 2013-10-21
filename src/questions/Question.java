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
@XmlType(propOrder = {"id", "used", "imageFilePath", "text", "answerList"})
public class Question {

    private int id;
    private String imageFilePath;
    private String text;
    private List<Answer> answerList;
    private boolean used = false;

    public Question() {
        imageFilePath = "";
    }
    
    public Question(int id, String imageFilePath, String text, List<Answer> answerList) {
        this.id = id;
        this.imageFilePath = imageFilePath;
        this.text = text;
        this.answerList = answerList;
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
    
    public void setText(String text){
        this.text = text;
    }
    
    @XmlAttribute(name="used")
    public boolean getUsed() {
        return used;
    }
    
    public void setUsed(boolean used) {
        this.used = used;
    }
 
    @XmlAttribute(name="imageFilePath")
    public String getImageFilePath() {
        return imageFilePath;
    }


    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }
    

    @XmlElementWrapper(name = "answerList")
    @XmlElement(name = "answer")
    public List<Answer> getAnswerList() {
        return answerList;
    }


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
