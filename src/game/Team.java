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
    private ArrayList <Employee> members = new ArrayList();
    
    public Team(String name, Employee leader) {
        this.name = name;
        this.leader = leader;
    }
    
    public String getName(){
        return name;
    }
    
    public Employee getLeader(){
        return leader;
    }
    
    public void destroyTeam(){
        this.leader = null;
        this.members.clear();
        this.name = null;
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
    
    // TODO - Kan det eksistere et team uten teamleder?? Lars Aksel
    public void removeTeamLeader() {
        if (this.leader != null) {
            this.leader.setPay(leader.getPay() - 2000);
            leader = null;
        }
    }
    
    public ArrayList getMembers(){
        return this.members;
    }
    
    public Employee getMember(int i){
        return this.members.get(i);
    }
    
    public int getNumberOfMembers() {
        return members.size();
    }
    
    @Override
    public String toString(){
        return "Team name: " + getName() + ", and their leader is: " + getLeader();
    }
    
}
