package xml;

import game.Employee;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        //readQuestions();
        //readEmployees();
        //sortAndWriteEmployees();
    }

    public void createEmployees() {
        EmployeeMaster em = new EmployeeMaster();

        ArrayList employees = new ArrayList();

        try {
            JAXBContext context = JAXBContext.newInstance(EmployeeMaster.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            //m.marshal(qm, System.out);
            m.marshal(em, new File("res/xml/employeeList.xml"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void sortAndWriteEmployees() {
        try {
            ArrayList<Employee> eList = (ArrayList) xml.XMLReader.getEmployees();
            Collections.sort(eList);
            EmployeeMaster em = new EmployeeMaster();
            em.setEmployeeList(eList);

            JAXBContext context = JAXBContext.newInstance(EmployeeMaster.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(em, new File("res/xml/employeeList.xml"));


        } catch (Exception ex) {
            Logger.getLogger(Testing.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void readQuestions() {
        try {
            ArrayList<Question> qList = (ArrayList) xml.XMLReader.getQuestions();
            for (Question q : qList) {
                System.out.println(q.toString());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void readEmployees() {
        try {
            ArrayList<Employee> eList = (ArrayList) xml.XMLReader.getEmployees();
            for (Employee e : eList) {
                System.out.println(e.toString());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void writeQuestions() {
        ArrayList<Question> qList = new ArrayList();
        QuestionMaster qm = new QuestionMaster();

        List<Answer> al1 = new ArrayList();
        al1.add(new Answer("Ja", 0));
        al1.add(new Answer("Nei", 1));
        Question q1 = new Question(1, "", "https://en.wikipedia.org/wiki/Waterfall_development", "Er det standup meeting i fossefalls metoden?", al1);

        List<Answer> al2 = new ArrayList();
        al2.add(new Answer("10min", 1));
        al2.add(new Answer("30min", 0));
        al2.add(new Answer("Så lenge som trenges", 0));

        Question q2 = new Question(2, "test/testPath","https://en.wikipedia.org/wiki/Standup_meeting", "Standup meeting skal vare i ca. hvor lenge?", al2);

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
