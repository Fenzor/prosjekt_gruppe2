package xml;

import game.Employee;
import java.io.File;
import java.util.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import quiz.Question;

/**
 *
 * @author WaltherKammen
 * 
 * Henter data fra res/xml/*.xml
 */
public class XMLReader {

    /**
     *
     * @return en liste med spørsmål og svar
     * @throws Exception 
     */
    public static List<Question> getQuestions() throws Exception {
        File file = new File("res/xml/questionList.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(QuestionMaster.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        QuestionMaster el = (QuestionMaster) jaxbUnmarshaller.unmarshal(file);

        return el.getQuestionList();
    }

    /**
     *
     * @return en liste med ansatte
     * @throws Exception
     */
    public static List<Employee> getEmployees() throws Exception {
        File file = new File("res/xml/employeeList.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(EmployeeMaster.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        EmployeeMaster em = (EmployeeMaster) jaxbUnmarshaller.unmarshal(file);

        return em.getEmployeeList();
    }
}
