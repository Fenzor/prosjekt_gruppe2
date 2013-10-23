/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Rasmus
 */
public class Team implements Serializable {

    private String name;
    private Employee leader;
    private static int numberOfTeams;
    private ArrayList <Employee> members = new ArrayList();
    
    public Team(String name, Employee leader) {
        this.name = name;
        this.leader = leader;
        numberOfTeams++;
    }
    
    public String getName(){
        return name;
    }
    
    public String getLeader(){
        return leader.getName();
    }
    
    public void destroyTeam(){
        this.leader = null;
        this.members.clear();
        this.name = null;
        numberOfTeams--;
    }
    
    public void setLeader(Employee emp){
        this.leader = emp;
        emp.setPay(emp.getPay() + 2000);
    }
    
    public void addTeamMember(Employee emp){
        this.members.add(emp);
    }
    
    public void removeTeamMember(Employee emp){
        this.members.remove(emp);
    }
    
    public void removeTeamLeader(Employee emp){
        emp.setPay(emp.getPay() - 2000);
        this.members.remove(emp);
    }
    
    public ArrayList getMembers(){
        return this.members;
    }
    
    public Employee getMember(int i){
        return this.members.get(i);
    }
    
    public static int getNumberOfTeams(){
        return numberOfTeams;
    }
    
    public String toString(){
        return "Team name: " + getName() + ", and their leader is: " + getLeader();
    }
    
}
