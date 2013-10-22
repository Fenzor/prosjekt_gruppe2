/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import questions.Question;
import xml.QuestionMaster;

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
    private ArrayList<Question> questions = new ArrayList();
    private String fileName = "";
    BufferedReader br = null;

    public DevelopmentModel(Type type, int modelId) {
        this.type = type;
        this.modelId = modelId;
    }

    public Type getType() {
        return this.type;
    }

    public static List<Question> getQuestions() throws Exception {
        File file = new File("res/xml/questionList.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(QuestionMaster.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        QuestionMaster el = (QuestionMaster) jaxbUnmarshaller.unmarshal(file);

        return el.getQuestionList();
    }
/*
    public ArrayList getRndQuestion(int id) throws Exception {
        Random rg = new Random();
        int numberOfIndexWithID = 0;
        for(int i = 0; i < getQuestions().size(); i++){
            if(getQuestions().get(i).getId() == id){
                numberOfIndexWithID += 1;
            }
        }
        
        int randomNumber = rg.nextInt(numberOfIndexWithID - 1);
        int numberOfAnswers = getQuestions().get(randomNumber).getAnswerList().size();
        String question = getQuestions().get(randomNumber).getText();
        int correctIndex = -1;
        boolean correctAnswer = false;
        for (int i = 0; i < numberOfAnswers; i++){
            if(getQuestions().get(randomNumber).getAnswerList().get(i).getScore() == 1){
                correctIndex = i;
                correctAnswer = true;
                 questions.add(new Question(question,getQuestions().get(randomNumber).getAnswerList().get(i).getAnswerText(),correctAnswer));
            }else{
                questions.add(new Question(question,getQuestions().get(randomNumber).getAnswerList().get(i).getAnswerText(),correctAnswer));
            }
            
        }
        return questions;
    }
*/
    public enum Type {

        UP, Scrum, Waterfall
    }

    public int getModelId() {
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
//    public void readFileIDK6() {
//        try {
//            String currentLine;
//
//            br = new BufferedReader(new FileReader("/Users/Dahl/NetBeansProjects/prosjekt_gruppe2/src/data/questions.txt"));
//
//            while ((currentLine = br.readLine()) != null) {
//                questions.add(currentLine);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (br != null) {
//                    br.close();
//                }
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }

//    public void getQuestions() {
//        readFileIDK6();
//        for (int i = 0; i < questions.size(); i++) {
//            System.out.println(questions.get(i));
//        }
//    }
    public String toString() {
        return "Model: " + getType() + ", and has the ID: " + getModelId();
    }
}
