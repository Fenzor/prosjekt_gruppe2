/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Rasmus
 */
public class World {
    private Player player;
    private int gameTime;
    private boolean paused;
    private double millis;
    private double lastMillis;
    private double ticks_per_MS = 0.4;
    private double ticksPassed;
    private double currentTicks;
    private double lastTicks;
    
    
    // Testing pågår, disregard.
    
    
    
    public static void main(String[] args) throws InterruptedException{
        World game = new World();
        game.run();
        
    }
    
    public void run() throws InterruptedException {
        initialize("Rasmus","Tycoon");
        
        
        while (gameTime<400){
            // Her går kode for å se om endringer har blitt / skal bli gjort
            if(!paused){
                millis  = System.currentTimeMillis();
                ticksPassed = (millis - lastMillis)*ticks_per_MS;
                currentTicks = lastTicks + ticksPassed;
                if(currentTicks - lastTicks>400){
                    lastMillis = millis;
                    lastTicks = currentTicks;
                    ticksPassed = 0;
                    System.out.println(getDate(gameTime));
                    endMonth();
                    gameTime+=1;
                    //testage();
                } else {
                    Thread.sleep(100);
                }
            } else {
                
            }
        }
    }
    
     //public void testage(){
     //   
    //    if(gameTime==3){
    //        this.player.getCompany().assignTeam(player.getCompany().getTeam(0), player.getCompany().getEmployee(0));
    //        this.player.getCompany().assignTeam(player.getCompany().getTeam(0), player.getCompany().getEmployee(1));
    //        this.player.getCompany().assignTeam(player.getCompany().getTeam(0), player.getCompany().getEmployee(4));
    //        this.player.getCompany().assignTeam(player.getCompany().getTeam(0), player.getCompany().getEmployee(3));
    //    }
    //    if(gameTime==5){
    //        this.player.getCompany().takeProject(player.getCompany().getProjectAtIndex(0));
    //        this.player.getCompany().assignTeamToProject(player.getCompany().getTeam(0), player.getCompany().getProjectAtIndex(0));
    //   }
    //}
    
    
    public int getTime(){
        return gameTime;
    }
    
    public String getDate(int ix){
        String monthString;
        int year;
        year = 1980 + ix/12;
        
        switch (ix % 12) {
            case 0:  monthString = "January";
                     break;
            case 1:  monthString = "February";
                     break;
            case 2:  monthString = "March";
                     break;
            case 3:  monthString = "April";
                     break;
            case 4:  monthString = "May";
                     break;
            case 5:  monthString = "June";
                     break;
            case 6:  monthString = "July";
                     break;
            case 7:  monthString = "August";
                     break;
            case 8:  monthString = "September";
                     break;
            case 9: monthString = "October";
                     break;
            case 10: monthString = "November";
                     break;
            case 11: monthString = "December";
                     break;
            default: monthString = "Invalid month";
                     break;
        }
        return monthString + ", " + year;
    }
    
    private void initialize(String playerName, String companyName){
        this.gameTime = 0;
        millis = 0;
        lastMillis = 0;
        lastTicks = 0;
        paused = false;
        player = new Player(playerName);
        Company comp = new Company(companyName);
        player.setCompany(comp);
        //Project prosjekt = new Project("Prosjekt1", 7, 1, 300000, this);
        //Employee Asgeir = new Employee("Asgeir", Employee.sex.male, false);
        //player.getCompany().hireEmployee(Asgeir); 
        //Employee Lars = new Employee("Lars", Employee.sex.male, false);
        //player.getCompany().hireEmployee(Lars); 
        //Employee Walther = new Employee("Walther", Employee.sex.male, false);
        //player.getCompany().hireEmployee(Walther); 
        //Employee Andreas = new Employee("Andreas", Employee.sex.male, false);
        //player.getCompany().hireEmployee(Andreas); 
        //Employee Rasmus = new Employee("Rasmus", Employee.sex.male, false);
        //player.getCompany().hireEmployee(Rasmus); 
        //Team Ateam = new Team("A-Team", Walther);
        //player.getCompany().createTeam(Ateam);
        //this.player.getCompany().takeProject(prosjekt);
        
        
    }
    
    private void endMonth(){ //betale ansatte, beregne fortløpende kostnader på ulike prosjekt.
        
        player.getCompany().updateGlobalTimeUsed();
        for (int i = 0; i<player.getCompany().getProjects().size();i++){
            Project p = player.getCompany().getProjectAtIndex(i);
            p.updateCost(player.getCompany().getMonthlyProjectCost(p));
            if (p.getTimeUsed()>p.getTimeEstimated()){
                player.getCompany().endProject(p, this);
            }
        }
        player.getCompany().payEmployees();
    }
    
    public void saveGame(){
        try{
            // Åpner en fil som skrive til
            FileOutputStream saveFile = new FileOutputStream("SaveObjects.sav");
            //Lager en ObjectOutputStream til å lagre objekter til filen det skal lagres.
            ObjectOutputStream save = new ObjectOutputStream(saveFile);
            //Lagrer
            save.writeObject(player.getCompany());
            //Lukker filen.
            save.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
     public Company loadGame(){
        try{
            //Åpner filen som skal leses fra
            FileInputStream saveFile = new FileInputStream("SaveObjects.sav");
            //Lager en ObjectInputStream til å hente objekter fra den lagrede filen.
            ObjectInputStream save = new ObjectInputStream(saveFile);
            player.setCompany((Company) save.readObject());
        }catch(Exception e){
            e.printStackTrace();
        }
        return player.getCompany();
    }
    
    public void pauseGame(){
        this.paused = true;
    }
    
    public void resumeGame(){
        this.paused = false;
    }
    
}
