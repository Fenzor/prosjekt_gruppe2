package game;

import game.DevelopmentModel.Type;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rasmus
 */
public class World implements Serializable, Runnable {

    private Player player;
    private Company company;
    private int gameTime;
    private boolean paused;
    private double millis;
    private double lastMillis;
    private double ticks_per_MS = 1;
    // sets gamespeed, 1 = 1 month per second, 2 = 2 months per second
    private double ticksPassed;
    private double currentTicks;
    private double lastTicks;
    private Thread worldThread;
    private boolean isRunning;

    // Testing pågår, disregard.
    public World(String playername, String companyname) {
        initialize(playername, companyname);
        populateEmployees();
        populateDevelopmentModels();
        populateProjects();
    }

    /*
     Runs the world in a separate thread...
     */
    public void init() {
        this.worldThread = new Thread(this);
        this.worldThread.start();
    }

    private void populateEmployees() {
        try {
            this.company.setEmployee((ArrayList) xml.XMLReader.getEmployees());
        } catch (Exception ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void populateDevelopmentModels() {
        ArrayList<DevelopmentModel> devMod = new ArrayList();
        devMod.add(new DevelopmentModel(Type.Scrum, 1));
        devMod.add(new DevelopmentModel(Type.UP, 2));
        devMod.add(new DevelopmentModel(Type.Waterfall, 3));
        this.company.setDevelopmentModels(devMod);
    }

    private void populateProjects() {
        ArrayList<Project> prosjekt = new ArrayList();
        prosjekt.add(new Project("Chatsystem", "Skal kunne snakke med andre", 10, 12, 5, 600, this.company.getDevelopmentModels().get(0)));
        prosjekt.add(new Project("Tur App", "Skal kunne registrere turen en person har gått", 4, 6, 4, 200, this.company.getDevelopmentModels().get(1)));
        prosjekt.add(new Project("Servicekalkulator", "Skal kunne kalkulere pris for service av de nye bil modellene", 12, 14, 9, 700,
                this.company.getDevelopmentModels().get(2)));
        this.company.setProjects(prosjekt);
    }

    @Override
    public void run() {
        isRunning = true;
        while (gameTime < 400 && isRunning) {
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
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {

                    }
                }
            } else {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {

                }
            }
        }
    }

    public int getGameTime() {
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
        this.company = comp;

    }

    // Betale ansatte, beregne fortløpende kostnader på ulike prosjekt.
    private void endMonth() {
        this.company.updateGlobalTimeUsed();
        for (int i = 0; i < this.company.getProjects().size(); i++) {
            Project p = this.company.getProjectAtIndex(i);
            p.updateCost(this.company.getMonthlyProjectCost(p));
            if (p.getTimeUsed() > p.getTimeEstimated()) {
                this.company.endProject(p, this);
            }
        }
        this.company.payEmployees();
    }

    public synchronized void pause() {
        System.out.println("Game Paused");
        this.paused = true;
    }

    public synchronized void resume() {
        System.out.println("Game Resumed");
        this.paused = false;
    }

    public void destroy() {
        this.isRunning = false;
    }

    public Company getCompany() {
        return this.company;
    }

    public String getInformationTable() {
        return "Date: " + this.getDate(gameTime)
                + "\n" + this.player.toString()
                + "\nCompany: " + this.company.getName()
                + "\nNo. employees: " + this.company.getEmployees().size()
                + " ,Monthly pay: " + this.company.getMonthlyPay()
                + "\nCash: " + this.company.getCash();
    }

    public static void main(String... args) {
        World w = new World("Ola Nordman", "Selskap A/S");
        w.init();
    }
}
