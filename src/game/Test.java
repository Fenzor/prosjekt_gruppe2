/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.ArrayList;

/**
 *
 * @author Dahl
 */
public class Test {

    public static void main(String[] args) {
        ArrayList<Employee> ansatte = new ArrayList<Employee>();
        ArrayList<Team> lag = new ArrayList<Team>();
        ArrayList<Project> prosjekt = new ArrayList<Project>();

        Employee employee = new Employee("Tester", 5, 50, 250, Employee.sex.male, false);
        Employee leader = new Employee("Leder", 10, 100, 500, Employee.sex.male, false);

        DevelopmentModel devmod = new DevelopmentModel(DevelopmentModel.Type.Scrum, 1);
        Team teams = new Team("TeamOne", leader);
        Project project = new Project("Chatsystem", "Skal kunne snakke med andre", 10, 12, 5, 600, devmod);

        ansatte.add(employee);
        ansatte.add(leader);
        lag.add(teams);
        prosjekt.add(project);
        
        Company company = new Company("Test AS", 9, 1000, ansatte, lag, prosjekt);

        Player player = new Player("TheOne", company, 1000);
        
        //player.saveGame();

    }
}
