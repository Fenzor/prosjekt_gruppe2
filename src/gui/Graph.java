/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

/**
 *
 * @author larsat
 */
public class Graph extends Sprite {
    private int maxPoints;
    private DataValues[] dataValues;
  
    public Graph(int maxPoints, float xPos, float yPos, float sizeX, float sizeY) {
        super(xPos, yPos, sizeX, sizeY);
        this.maxPoints = maxPoints;
        this.dataValues = new DataValues[maxPoints];
    }
    
    
    public void addValue(float value, String label) {
        
    }
    
    private class DataValues {
        private float value;
        private String label;
        
        public DataValues(float value, String label) {
            this.value = value;
            this.label = label;
        }

        public float getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }
}
