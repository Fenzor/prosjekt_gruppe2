package game;

import java.io.Serializable;
import java.util.Random;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 *
 */
@XmlRootElement(name = "Employee")
@XmlType(propOrder = {"name", "sex", "skill", "xp", "pay", "isSick"})
public class Employee implements Serializable {

    private String name;
    private int skill, xp, pay;
    private Employee.sex sex;
    private boolean isSick;

    public Employee() {
        isSick = false;
    }

    public Employee(String namestring, int s, int x, Employee.sex sex, int p, boolean sick) {
        name = namestring;
        skill = s;
        xp = x;
        this.sex = sex;
        pay = p;
        isSick = sick;
    }

    public Employee(String namestring, Employee.sex sex, boolean sick) {
        name = namestring;
        Random rg = new Random();
        skill = rg.nextInt(10);
        xp = 0;
        this.sex = sex;
        isSick = sick;
        pay = (skill * 800) + rg.nextInt(4) * 1000;
    }

    public Employee(String namestring, Employee.sex sex) {
        name = namestring;
        Random rg = new Random();
        skill = rg.nextInt(10);
        xp = 0;
        this.sex = sex;
        isSick = false;
        pay = (skill * 800) + rg.nextInt(4) * 1000;
    }

    public Employee(Employee employee) {
        name = employee.getName();
        skill = employee.getSkill();
        xp = employee.getXp();
        sex = employee.getSex();
        pay = employee.getPay();
        isSick = employee.isIsSick();
    }

    public enum sex {

        male, female
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public int getSkill() {
        return skill;
    }

    public void setSkill(int newSkill) {
        this.skill = newSkill;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int newXp) {
        this.xp = newXp;
    }

    public sex getSex() {
        return sex;
    }

    public void setSex(sex sex) {
        this.sex = sex;
    }

    public boolean isIsSick() {
        return isSick;
    }

    public void setIsSick(boolean isSick) {
        this.isSick = isSick;
    }

    public int getPay() {
        return pay;
    }

    public void setPay(int newPay) {
        this.pay = newPay;
    }

    public void changeSkill(int skill) {
        this.skill += skill;
    }

    public String toString() {
        return "Name" + getName() + ", and has the skill lvl:" + getSkill() + ". Xp is: " + getXp() + ", and the person is : " + getSex() + ". The persons pay is: " + getPay() + ". The person is sick: " + isIsSick();
    }
}
