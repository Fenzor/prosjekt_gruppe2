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
public class Project implements Serializable {

    private String name; //Prosjekttittel, unike for hvert prosjekt for å enkelt kjenne de igjen inne i spillet
    private String info; //Generell beskrivelse av prosjektet. Kan genereres på bakgrunn av andre faktorer og prosjekttype.
    private int timeEstimated; //Tid prosjektet krever (estimert, kan endres underveis?)
    private int timeUsed; //Tid brukt på prosjektet (faktisk brukte timer, endre "timeEstimated" om tid går til spille)
    private int deadline; //Deadline, definert på datoformat, bruk logikk i World
    private int complexity; //Hvor komplisert og eksprimentelt prosjektet er
    private int brilliance; //Hvor feilfritt og nyvinnende prosjektet er utført
    private int pay; //Avtalt betaling for prosjektet
    private int cost; //Penger brukt på prosjektet (alle utgifter og lønn)
    private ArrayList<Team> teams = new ArrayList();
    private DevelopmentModel devMod;
    private ProjectType ProType;

    public Project(String name, String info, int timeEstimated, int deadline, int complexity, int pay, DevelopmentModel devMod) {
        this.name = name;
        this.info = info;
        this.timeEstimated = timeEstimated;
        this.timeUsed = 0;
        this.deadline = deadline;
        this.complexity = complexity;
        this.brilliance = 0;
        this.pay = pay;
        this.cost = 0;
        this.devMod = devMod;
    }
    
    public Project(String name, int deadline, int complexity, int pay, World w){
        this.name = name;
        this.deadline = this.setDeadlineProper(w, deadline);
        this.complexity = complexity;
        this.timeEstimated = this.estimateTime();
        this.pay = pay;
        this.cost = 0;
    }
    

    public String getName() {
        return name;
    }
    
    public int estimateTime(){
        //beregner omtrent timer som trengs på et prosjekt, mellom 160 timer for enkleste og 7594 timer for en kompleksitet på 10.
        return this.timeEstimated = this.complexity*80+(((int)Math.pow(this.complexity,3)-1)*6); 
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getTimeEstimated() {
        return timeEstimated;
    }

    public void setTimeEstimated(int timeEstimated) {
        this.timeEstimated = timeEstimated;
    }

    public int getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(int timeUsed) {
        this.timeUsed = timeUsed;
    }

    public String getQuestion(){
        return devMod.getRndQuestion();
    }
    
    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }
    
    public int setDeadlineProper(World w, int dl){ //setter deadline relativt til spilltid
        return w.getTime()+dl;
    }
    
    public String getDeadlinePrint(World w){ //skriver ut deadline som spilldato
        return w.getDate(this.getDeadline());
    }

    public int getComplexity() {
        return complexity;
    }

    public void setComplexity(int complexity) {
        this.complexity = complexity;
    }

    public int getBrilliance() {
        return brilliance;
    }

    public void setBrilliance(int brilliance) {
        this.brilliance = brilliance;
    }
    
    public void addTimeUsed(int i){
        this.timeUsed += i;
    }
    
    public int getNumberOfEmployeesPerProject(){
        int emp = 0;
        
        for(int i = 0; i<teams.size();i++){
            
            emp += teams.get(i).getMembers().size();
        }
        return emp;
    }

    public int getPay() {
        return pay;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
    
    public void updateCost(int cost){
        this.cost += cost;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }
    
    public void addTeam(Team team){
        this.teams.add(team);
    }
    
    public void removeTeam(Team team){
        this.teams.remove(team);
    }
    
    public void removeTeams(){
        this.teams.clear();
    }

    public DevelopmentModel getDevMod() {
        return devMod;
    }

    public void setDevMod(DevelopmentModel devMod) {
        this.devMod = devMod;
    }

    public ProjectType getProType() {
        return ProType;
    }

    public void setProType(ProjectType ProType) {
        this.ProType = ProType;
    }
    
    public String toString(){
        return getName() + getInfo() + getTimeEstimated() + getTimeUsed() + getDeadline() + getComplexity() + getBrilliance() + getNumberOfEmployeesPerProject() + getPay() + getCost() + getDevMod().toString(); 
    }
}