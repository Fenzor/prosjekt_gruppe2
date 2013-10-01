package xml;

import game.Employee;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author WaltherKammen
 */
@XmlRootElement(namespace = "listOfEmployees")
public class EmployeeMaster {

    private List<Employee> employeeList;

    /**
     *
     * @param employeeList
     */
    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    /**
     *
     * @return
     */
    @XmlElementWrapper(name = "employeekList")
    @XmlElement(name = "employee")
    public List<Employee> getEmployeeList() {
        return employeeList;
    }
}
