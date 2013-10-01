/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Asgeir
 */
public class DevelopmentModel {

    /*
     * Klasse for å lese inn og håndterer utviklingsmodeller. Data hentes i fil.
     * Eksempler på modeller: Scrum, Waterfall, UP
     * 
     * Hver modell har konstanter som sier noe om hvor vanskelige etc de
     * normalt er.
     * Hver modell har minst ett unikt spørsmål knyttet til seg som er 
     * spesifikt om utfordringer og fakta om modellen.
     * 
     */
    public String model;
    public int modelId;
    private ArrayList questions = new ArrayList();
    private String fileName = "";
    BufferedReader br = null;

    public DevelopmentModel(String model, int modelId) {
        this.model = model;
        this.modelId = modelId;
    }
    
    public String getModel(){
        return model;
    }
    
    public int getModelId(){
        return modelId;
    }

    public DevelopmentModel() {
    }

//    public void readFile(String fileName) throws IOException{
//        try (BufferedReader reader = new BufferedReader(new FileReader("/Users/Dahl/NetBeansProjects/prosjekt_gruppe2/src/data/questions.txt"))){
//            String currentLine;
//            
//            while((currentLine = reader.readLine()) != null){
//                questions.add(currentLine);
//            }
//        } catch (IOException e){
//            e.printStackTrace();
//        }
//        
//    }
    public void readFileIDK6() {
        try {
            String currentLine;

            br = new BufferedReader(new FileReader("/Users/Dahl/NetBeansProjects/prosjekt_gruppe2/src/data/questions.txt"));

            while ((currentLine = br.readLine()) != null) {
                questions.add(currentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void getQuestions() {
        readFileIDK6();
        for (int i = 0; i < questions.size(); i++) {
            System.out.println(questions.get(i));
        }
    }
    
    public String toString(){
        return "Model: " + getModel() + ", and has the ID: " + getModelId();
    }
}
