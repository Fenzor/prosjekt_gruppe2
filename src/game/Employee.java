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
public class Employee implements Serializable, Comparable {

    private String name;
    private int skill;
    private int xp;
    private int pay;
    private Employee.Sex sex;
    private boolean isSick;
    
    public Employee() {
        isSick = false;
    }

    public Employee(String name, int skill, int xp, int pay, Employee.Sex sex, boolean isSick) {
        this.name = name;
        this.skill = skill;
        this.xp = xp;
        this.sex = sex;
        this.pay = pay;
        this.isSick = isSick;
    }

    public Employee(String namestring, Employee.Sex sex, boolean sick) {
        name = namestring;
        Random rg = new Random();
        //Ferdighet settes til et tall mellom 0 og 10.
        skill = rg.nextInt(10);
        xp = 0;
        this.sex = sex;
        isSick = sick;
        //Lønn settes til mellom 0 og 12000, avhengig av ferdighet.
        pay = (skill * 800) + rg.nextInt(4) * 1000;
    }

    public Employee(String namestring, Employee.Sex sex) {
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

    @Override
    public int compareTo(Object t) {
        return name.compareTo(((Employee) t).name);
    }

    public enum Sex {
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
    
    public void changeXp(int Xp){
        this.xp += Xp;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
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
        if (this.skill+skill>10) this.skill =10;
        else if (this.skill+skill<0) this.skill = 0;
        else this.skill += skill;
        this.setPay(this.pay + skill*800);
    }

    @Override
    public String toString() {
        return "Name: " + getName() + "\tSkill lvl: " + getSkill() + "\tXp: " + getXp() + "\tSex: " + getSex() + "\tPay: " + getPay() + "\tSick: " + isIsSick();
    }
}
