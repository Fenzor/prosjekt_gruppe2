package xml;

import game.Employee;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import questions.Answer;
import questions.Question;

/**
 *
 * @author WaltherKammen
 */
class Testing {
    public static void main(String[] args) {
        writeQuestions();
        readQuestions();
        //readEmployees();
    }
    
    public static void readQuestions() {
        try {
            ArrayList<Question> qList = (ArrayList) xml.XMLReader.getQuestions();
            for(Question q:qList) {
            System.out.println(q.toString());
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }
    
    public static void readEmployees() {
        try {
            ArrayList<Employee> eList = (ArrayList) xml.XMLReader.getEmployees();
            for(Employee e:eList) {
            System.out.println(e.toString());
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }
    
    public static void writeQuestions() {
        ArrayList<Question> qList = new ArrayList();
        QuestionMaster qm = new QuestionMaster();
        
        List<Answer> al1 = new ArrayList();
        al1.add(new Answer("Ja", 0));
        al1.add(new Answer("Nei", 1));
        Question q1 = new Question(1, "", "Er det standup meeting i fossefalls metoden?", al1);
        
        List<Answer> al2 = new ArrayList();
        al2.add(new Answer("Use  diagram", 0));
        al2.add(new Answer("Problem domene modell", 1));
        al2.add(new Answer("Sekvens diagram", 0));
        
        Question q2 = new Question(2, "test/testPath", "Hva viser bildet?", al2);
        
        qList.add(q1);
        qList.add(q2);
        
        qm.setQuestionList(qList);
        
        try {
            JAXBContext context = JAXBContext.newInstance(QuestionMaster.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            //m.marshal(qm, System.out);
            m.marshal(qm, new File("res/xml/questionList.xml"));
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }
}
