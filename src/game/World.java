package game;

import game.DevelopmentModel.Type;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private double ticks_per_MS = 1;
    // sets gamespeed, 1 = 1 month per second, 2 = 2 months per second
    private double ticksPassed;
    private double currentTicks;
    private double lastTicks;

    // Testing pågår, disregard.
    public World(String playername, String companyname) {
        initialize(playername, companyname);
        populateEmployees();
        populateDevelopmentModels();
        populateProjects();
    }
    
    private void populateEmployees() {
        try {
            player.getCompany().setEmployee((ArrayList) xml.XMLReader.getEmployees());
        } catch (Exception ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void populateDevelopmentModels() {
        ArrayList<DevelopmentModel> devMod = new ArrayList();
        devMod.add(new DevelopmentModel(Type.Scrum, 1));
        devMod.add(new DevelopmentModel(Type.UP, 2));
        devMod.add(new DevelopmentModel(Type.Waterfall, 3));
        player.getCompany().setDevelopmentModels(devMod);
    }

    
    private void populateProjects() {
        ArrayList<Project> prosjekt = new ArrayList();
        prosjekt.add(new Project("Chatsystem", "Skal kunne snakke med andre", 10, 12, 5, 600, player.getCompany().getDevelopmentModels().get(0)));
        prosjekt.add(new Project("Tur App", "Skal kunne registrere turen en person har gått", 4, 6, 4, 200, player.getCompany().getDevelopmentModels().get(1)));
        prosjekt.add(new Project("Servicekalkulator", "Skal kunne kalkulere pris for service av de nye bil modellene", 12, 14, 9, 700, 
                player.getCompany().getDevelopmentModels().get(2)));
        player.getCompany().setProjects(prosjekt);
    }
 

    public void run() throws InterruptedException {


        while (gameTime < 400) {
            // Her går kode for å se om endringer har blitt / skal bli gjort
            if (!paused) {
                millis = System.currentTimeMillis();
                ticksPassed = (millis - lastMillis) * ticks_per_MS;
                currentTicks = lastTicks + ticksPassed;
                if (currentTicks - lastTicks > 1000) {
                    lastMillis = millis;
                    lastTicks = currentTicks;
                    ticksPassed = 0;
                    System.out.println(getDate(gameTime));
                    endMonth();
                    gameTime += 1;
                    //testage();
                } else {
                    Thread.sleep(100);
                }
            } else {
            }
        }
    }

    /*
     public void testage(){
       
     if(gameTime==3){
     this.player.getCompany().assignTeam(player.getCompany().getTeam(0), player.getCompany().getEmployee(0));
     this.player.getCompany().assignTeam(player.getCompany().getTeam(0), player.getCompany().getEmployee(1));
     this.player.getCompany().assignTeam(player.getCompany().getTeam(0), player.getCompany().getEmployee(4));
     this.player.getCompany().assignTeam(player.getCompany().getTeam(0), player.getCompany().getEmployee(3));
     }
     if(gameTime==5){
     this.player.getCompany().takeProject(player.getCompany().getProjectAtIndex(0));
     this.player.getCompany().assignTeamToProject(player.getCompany().getTeam(0), player.getCompany().getProjectAtIndex(0));
     }
     }
     */
    public int getTime() {
        return gameTime;
    }

    public String getDate(int ix) {
        String monthString;
        int year;
        year = 1980 + ix / 12;

        switch (ix % 12) {
            case 0:
                monthString = "January";
                break;
            case 1:
                monthString = "February";
                break;
            case 2:
                monthString = "March";
                break;
            case 3:
                monthString = "April";
                break;
            case 4:
                monthString = "May";
                break;
            case 5:
                monthString = "June";
                break;
            case 6:
                monthString = "July";
                break;
            case 7:
                monthString = "August";
                break;
            case 8:
                monthString = "September";
                break;
            case 9:
                monthString = "October";
                break;
            case 10:
                monthString = "November";
                break;
            case 11:
                monthString = "December";
                break;
            default:
                monthString = "Invalid month";
                break;
        }
        return monthString + ", " + year;
    }

    private void initialize(String playerName, String companyName) {
        this.gameTime = 0;
        millis = 0;
        lastMillis = 0;
        lastTicks = 0;
        paused = false;
        player = new Player(playerName);
        Company comp = new Company(companyName);
        player.setCompany(comp);

    }

    private void endMonth() { //betale ansatte, beregne fortløpende kostnader på ulike prosjekt.

        player.getCompany().updateGlobalTimeUsed();
        for (int i = 0; i < player.getCompany().getProjects().size(); i++) {
            Project p = player.getCompany().getProjectAtIndex(i);
            p.updateCost(player.getCompany().getMonthlyProjectCost(p));
            if (p.getTimeUsed() > p.getTimeEstimated()) {
                player.getCompany().endProject(p, this);
            }
        }
        player.getCompany().payEmployees();
    }

    public void saveGame(String saveGameName) {
        try {
            // Åpner en fil som skrive til
            FileOutputStream saveFile = new FileOutputStream(saveGameName +".sav");
            //Lager en ObjectOutputStream til å lagre objekter til filen det skal lagres.
            ObjectOutputStream save = new ObjectOutputStream(saveFile);
            //Lagrer
            save.writeObject(player.getCompany());
            //Lukker filen.
            save.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Company loadGame(String loadGameName) {
        try {
            //Åpner filen som skal leses fra
            FileInputStream saveFile = new FileInputStream(loadGameName + ".sav");
            //Lager en ObjectInputStream til å hente objekter fra den lagrede filen.
            ObjectInputStream save = new ObjectInputStream(saveFile);
            player.setCompany((Company) save.readObject());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return player.getCompany();
    }

    public void pauseGame() {
        System.out.println("Game Paused");
        this.paused = true;
    }

    public void resumeGame() {
        System.out.println("Game Resumed");
        this.paused = false;
    }
}
