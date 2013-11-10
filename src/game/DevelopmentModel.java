/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import quiz.Question;
import xml.QuestionMaster;

/**
 *
 * @author Asgeir
 */
public class DevelopmentModel implements Serializable {

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
    private final ArrayList<Question> questions;
    private final String fileName = "";
    BufferedReader br = null;

    public DevelopmentModel(Type type, int modelId) {
        this.questions = new ArrayList();
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
    
    public enum Type {
        UP, Scrum, Waterfall
    }

    public int getModelId() {
        return modelId;
    }

    public DevelopmentModel() {
        this.questions = new ArrayList();
    }
    
    public String toString() {
        return "Model: " + getType() + ", and has the ID: " + getModelId();
    }
}
