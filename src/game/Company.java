/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.ArrayList;
import java.io.Serializable;

/**
 *
 * @author Rasmus
 */
public class Company extends World implements Serializable{
    
    private String name;
    private int rumor;
    private int cash;
    private ArrayList <Employee> employees = new ArrayList();
    private ArrayList <Team> teams = new ArrayList();
    private ArrayList <Project> projects = new ArrayList();
    
    public Company(String name){
        this.name = name;
        this.rumor = 0;
        this.cash = 200000;
    }
    
    public Company(String name, int rumor, int cash) {
        this.name = name;
        this.rumor = rumor;
        this.cash = cash;
    }
    
    public Company(String name, int rumor, int cash, ArrayList employee, ArrayList teams, ArrayList project){
        this.name = name;
        this.rumor = rumor;
        this.cash = cash;
        this.employees = employee;
        this.teams = teams;
        this.projects = projects;
    }
    
    public void hireEmployee(Employee emp){
        employees.add(emp);
    }  
    
    public void fireEmployee(Employee emp){
        employees.remove(emp);
    }
    
    public void payEmployees(){
        int pay = 0;
        for (int i = 0; i<employees.size();i++){
            pay += employees.get(i).getPay();
        }
        this.cash -= pay;
        System.out.println("employee cost this month: " + pay);
        System.out.println("Cash left: " + this.getCash());
    }
    
    public int getMonthlyProjectCost(Project p){
        int cost = 0;
        for (int i = 0; i<p.getTeams().size();i++){
            for(int j = 0;j<p.getTeams().get(i).getMembers().size();j++){
                cost += p.getTeams().get(i).getMember(j).getPay();
            }
        }
        return cost;
    }
    
    public void updateGlobalTimeUsed(){
        for (int i = 0; i<projects.size();i++){
            for (int j = 0; j<projects.get(i).getTeams().size();j++){
                for (int ii = 0;ii<projects.get(i).getTeams().get(j).getMembers().size();ii++){
                    projects.get(i).addTimeUsed(projects.get(i).getTeams().get(j).getMember(ii).getSkill()*2+30);
                }
            }
        }
    }
    
    public void assignTeamToProject(Team t, Project p){
        p.addTeam(t);
    }
    
    public void removeTeamFromProject(Team t, Project p){
        p.removeTeam(t);
    }
    
    
    
    public void takeProject(Project pro){
        this.projects.add(pro);
    }
    
    public void createTeam(Team t){
        this.teams.add(t);
    }
    
    public void assignTeam(Team t, Employee e){
        boolean canAdd = true;
        for (int i=0;i<teams.size();i++){
            for (int j=0;j<teams.get(i).getMembers().size();j++){
                if (teams.get(i).getMember(j) == e){
                    canAdd = false;
                    break;
                }
            }
        }
        if (canAdd) t.addTeamMember(e);
    }
    
    public ArrayList getProjects(){
        return projects;
    }
    
    public Project getProjectAtIndex(int index){
        return projects.get(index);
    }
    
    public void endProject(Project pro, World w){
        if (pro.getDeadline()>= w.getTime()){
            if(rumor < 10){
                rumor = rumor + pro.getComplexity()/10 + pro.getBrilliance()/10;
                if (rumor > 10) rumor = 10;
            }
            this.getPay(pro.getPay());
            System.out.println("Project " + pro.getName() + " ended and " + pro.getPay() + " was added to your bank account.");
        } else {
            this.getPay((int)(pro.getPay()*0.8));
            System.out.println("Project " + pro.getName() + " ended after deadline and " + (int)(pro.getPay()*0.8) + " was added to your bank account.");
        }
        pro.removeTeams();
        this.projects.remove(pro);
    }
    
    public void getPay(int i){
        this.cash += i;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public int getRumor() {
        return rumor;
    }

    public void setRumor(int newRumor) {
        this.rumor = newRumor;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int newCash) {
        this.cash = newCash;
    }
    
    public Employee getEmployee(int i){
        return this.employees.get(i);
    }
    
    public ArrayList getEmployees(){
        return employees;
    }

    public String getEmployeesString() {
        for (int i = 0; i < employees.size(); i++) {
            return employees.get(i).toString();
        }
        return null;
    }
    
    public Team getTeam(int i){
        return this.teams.get(i);
    }
    
    public String getTeamsString(){
        for(int i = 0; i < teams.size(); i++){
            return teams.get(i).toString();
        }
        return null;
    }
    
    public String getProjectsString(){
        for(int i = 0; i < projects.size(); i++){
            return projects.toString();
        }
        return null;
    }

    public String toString() {
        return "Company name:" + getName() + ", has rumor of:" + getRumor() + ", and has the amount of liquidity: " + getCash() + ", the company has the employees:" + getEmployeesString() + getTeamsString() + getProjectsString();
    }
}
