/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

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
    private Type type;
    public int modelId;
    private ArrayList<String> questions = new ArrayList();
    private String fileName = "";
    BufferedReader br = null;

    public DevelopmentModel(Type type, int modelId) {
        this.type = type;
        this.modelId = modelId;
    }
    
    public Type getType(){
        return this.type;
    }
    
    public String getRndQuestion(){
        Random rg = new Random();
        return questions.get(rg.nextInt(questions.size()-1));
    }
    
    public enum Type {

        UP, Scrum, Waterfall
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
        return "Model: " + getType() + ", and has the ID: " + getModelId();
    }
}
