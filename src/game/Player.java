package game;

/**
 *
 * @author Rasmus
 */
public class Player {

    private String name;
    private Company company;
    private int score;
//    private ArrayList<Company> companys = new ArrayList<Company>();
    
    public Player(String name, Company company, int score){
        this.name = name;
        this.company = company;
        this.score = score;
    }
    
    public Player(){
        
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
    
    public void setCompany(Company comp){
        this.company = comp;
    }
    
    public Company getCompany(){
        return this.company;
    }
    
    public Player(String name){
        this.name = name;
        this.score = 0;
    }
    
    public String toString(){
        return this.name;
    }
    
}
