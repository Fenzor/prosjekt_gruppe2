package xml;

import game.Employee;
import java.io.File;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;


/**
 *
 * @author WaltherKammen
 */
public class Test {

    public static void main(String[] args) {
        
        EmployeeMaster em = new EmployeeMaster();
        
        Employee employee1 = new Employee();
        employee1.setName("Walther");
        employee1.setPay(100);
        employee1.setSkill(50);
        employee1.setXp(500);
        employee1.setSex(Employee.sex.male);
        
        Employee employee2 = new Employee();
        employee2.setName("Bob");
        employee2.setPay(50);
        employee2.setSkill(40);
        employee2.setXp(30);
        employee2.setSex(Employee.sex.male);
        
        ArrayList<Employee> eList = new ArrayList<>();
        eList.add(employee1);
        eList.add(employee2);
        em.setEmployeeList(eList);

        try {
        JAXBContext context = JAXBContext.newInstance(EmployeeMaster.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        m.marshal(em, System.out);
        m.marshal(em, new File("res/xml/employeeList.xml"));
        } catch (Exception e) {
            
        }
        
    }
}
