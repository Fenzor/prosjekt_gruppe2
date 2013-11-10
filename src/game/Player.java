package game;

import java.io.Serializable;

/**
 *
 * @author Rasmus
 */
public class Player implements Serializable {
    private String name;
    private int score;
    
    public Player(String name){
        this.name = name;
        this.score = 0;
    }
    
    public Player(String name, int score){
        this.name = name;
        this.score = score;
    }  
    
    public String getName() {
        return name;
    }
    
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    public void changeScore(int score){
        this.score += score;
    }
    
    @Override
    public String toString(){
        return "Player: " + this.name + " ,Score: " + this.score;
    }
    
}
