package xml;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import questions.Question;

/**
 *
 * @author WaltherKammen
 */
@XmlRootElement(namespace = "listOfQuestions")
public class QuestionMaster {

    private List<Question> questionList;

    /**
     *
     * @param questionList
     */
    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    /**
     *
     * @return
     */
    @XmlElementWrapper(name = "questionkList")
    @XmlElement(name = "question")
    public List<Question> getQuestionList() {
        return questionList;
    }
}
