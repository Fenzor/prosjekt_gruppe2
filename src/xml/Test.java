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


        Employee employee1 = new Employee("Walther", 100, 1000, 50, Employee.sex.male, false);
        Employee employee2 = new Employee("Bob", 35, 250, 50, Employee.sex.male, true);
        Employee employee3 = new Employee("Siri", 40, 400, 75, Employee.sex.female, false);
        Employee employee4 = new Employee("Geir", 9001, 95424, 500, Employee.sex.male, false);
        Employee employee5 = new Employee("Peir", 2, 0, 2, Employee.sex.male, false);

        ArrayList<Employee> eList = new ArrayList<>();
        eList.add(employee1);
        eList.add(employee2);
        eList.add(employee3);
        eList.add(employee4);
        eList.add(employee5);
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
