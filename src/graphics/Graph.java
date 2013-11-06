/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import org.lwjgl.opengl.GL11;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 *
 * @author larsat
 */
public class Graph extends Sprite {

    private final int maxPoints;
    DataValues[] dataValues;
    private boolean changed;
    private float minValue;
    private float maxValue;

    public Graph(int maxPoints, float xPos, float yPos, float sizeX, float sizeY) {
        super(xPos, yPos, sizeX, sizeY);
        this.maxPoints = maxPoints;
        if (maxPoints <= 1) {
            maxPoints = 2; // Must always be two or above to avoid graphical issues...
        }
        this.dataValues = new DataValues[maxPoints];
        //this.dataValues = new DataValues[]{new DataValues(-100, "Blah.."), new DataValues(300, "Lol"), new DataValues(320, "Lol"), new DataValues(-30, "Lol")};
        this.changed = false;
    }

    public void addValue(float value, String label) {
        for (int i = 1; i < dataValues.length; i++) {
            dataValues[i - 1] = dataValues[i];
        }
        dataValues[dataValues.length - 1] = new DataValues(value, label);
        this.changed = true;
    }

    public void setMinMax() {
        for (DataValues dv : dataValues) {
            if (dv != null) {
                if (dv.getValue() > maxValue) {
                    maxValue = dv.getValue();
                }
                if (dv.getValue() < minValue) {
                    minValue = dv.getValue();
                }
            }
        }
    }

    @Override
    public void draw() {
        //if (this.changed) {
        setMinMax();
        float margin = 20;
        // White backdrop...
        GL11.glColor3f(1, 1, 1);
        GL11.glBegin(GL11.GL_POLYGON);
        GL11.glVertex2f(this.xPos, this.yPos);
        GL11.glVertex2f(this.xPos + this.sizeX, this.yPos);
        GL11.glVertex2f(this.xPos + this.sizeX, this.yPos + this.sizeY);
        GL11.glVertex2f(this.xPos, this.yPos + this.sizeY);
        GL11.glEnd();

        // Black lines around...
        GL11.glColor3f(0, 0, 0);
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(this.xPos, this.yPos);
        GL11.glVertex2f(this.xPos + this.sizeX, this.yPos);
        GL11.glVertex2f(this.xPos + this.sizeX, this.yPos + this.sizeY);
        GL11.glVertex2f(this.xPos, this.yPos + this.sizeY);
        GL11.glEnd();

        // Moves origo where it should be, based on the dataset...
        GL11.glColor4f(0, 0, 0, 0.25f);
        float origo = this.yPos + 2 * margin;
        if (maxValue == 0 && minValue == 0) {
            origo += (this.sizeY - 3 * margin) / 2;
        } else if (maxValue <= 0 && minValue < 0) {
            origo += this.sizeY - 3 * margin;
        } else if (maxValue > 0 && minValue < 0) {
            origo += ((minValue * -1) / (maxValue + (minValue * -1))) * (this.sizeY - 3 * margin);
        }
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex2f(this.xPos + margin, origo);
        GL11.glVertex2f(this.xPos + this.sizeX - margin, origo);
        GL11.glEnd();

        // draws tabs on the origo-line where the values should be put...
        float tabs = 5;
        for (int i = 0; i < dataValues.length; i++) {
            GL11.glBegin(GL11.GL_LINES);
            GL11.glVertex2f(this.xPos + margin + (i * (this.sizeX - 2 * margin) / (this.dataValues.length - 1)), origo + tabs);
            GL11.glVertex2f(this.xPos + margin + (i * (this.sizeX - 2 * margin) / (this.dataValues.length - 1)), origo - tabs);
            GL11.glEnd();
        }
        Line2D.Double[] lines = new Line2D.Double[dataValues.length - 1];
        for (int i = 0; i < lines.length; i++) {
            if (dataValues[i] == null) continue;
            float x = this.xPos + margin + (i * (this.sizeX - 2 * margin) / (this.dataValues.length - 1));
            float y = (this.yPos + 2*margin) + ((this.sizeY - 3 * margin) * ((this.dataValues[i].getValue() + Math.abs(minValue)) / (maxValue + Math.abs(minValue))));
            float x2 = this.xPos + margin + ((i + 1) * (this.sizeX - 2 * margin) / (this.dataValues.length - 1));
            float y2 = (this.yPos + 2*margin) + ((this.sizeY - 3 * margin) * ((this.dataValues[i + 1].getValue() + Math.abs(minValue)) / (maxValue + Math.abs(minValue))));
            lines[i] = new Line2D.Double(x, y, x2, y2);
        }
        
        // Draws the value-lines on the graph...
        Line2D.Double origLine = new Line2D.Double(this.xPos + margin, origo, this.xPos + margin + (this.sizeX - 2 * margin), origo);
        GL11.glLineWidth(2);
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        for (Line2D.Double line : lines) {
            if (line == null) continue;
            if (line.intersectsLine(origLine)) {
                Point2D interSect = getIntersection(line, origLine);
                if (line.getP1().getY() > origo) {
                    GL11.glColor4f(0, 1, 0, 1);
                    GL11.glVertex2d(line.getP1().getX(), line.getP1().getY());
                    GL11.glVertex2d(interSect.getX(), interSect.getY());
                    GL11.glColor4f(1, 0, 0, 1);
                    GL11.glVertex2d(line.getP2().getX(), line.getP2().getY());
                } else {
                    GL11.glColor4f(1, 0, 0, 1);
                    GL11.glVertex2d(line.getP1().getX(), line.getP1().getY());
                    GL11.glVertex2d(interSect.getX(), interSect.getY());
                    GL11.glColor4f(0, 1, 0, 1);
                    GL11.glVertex2d(line.getP2().getX(), line.getP2().getY());
                }
            } else {
                if (line.getP1().getY() > origo) {
                    GL11.glColor4f(0, 1, 0, 1);
                    GL11.glVertex2d(line.getP1().getX(), line.getP1().getY());
                    GL11.glVertex2d(line.getP2().getX(), line.getP2().getY());
                } else {
                    GL11.glColor4f(1, 0, 0, 1);
                    GL11.glVertex2d(line.getP1().getX(), line.getP1().getY());
                    GL11.glVertex2d(line.getP2().getX(), line.getP2().getY());
                }
            }
        }
        GL11.glEnd();
        GL11.glLineWidth(1);
        GL11.glShadeModel(GL11.GL_SMOOTH);
       
        // Black lines around box inside the graph...
        GL11.glColor4f(0, 0, 0, 1);
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(this.xPos + margin, this.yPos + 2 * margin);
        GL11.glVertex2f(this.xPos + this.sizeX - margin, this.yPos + 2 * margin);
        GL11.glVertex2f(this.xPos + this.sizeX - margin, this.yPos + this.sizeY - margin);
        GL11.glVertex2f(this.xPos + margin, this.yPos + this.sizeY - margin);
        GL11.glEnd();

        this.changed = false;
        //}
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public float getMinValue() {
        return minValue;
    }

    public float getMaxValue() {
        return maxValue;
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
    
    public static Point2D getIntersection(final Line2D.Double line1, final Line2D.Double line2) {

        final double x1,y1, x2,y2, x3,y3, x4,y4;
        x1 = line1.x1; y1 = line1.y1; x2 = line1.x2; y2 = line1.y2;
        x3 = line2.x1; y3 = line2.y1; x4 = line2.x2; y4 = line2.y2;
        final double x = (
                (x2 - x1)*(x3*y4 - x4*y3) - (x4 - x3)*(x1*y2 - x2*y1)
                ) /
                (
                (x1 - x2)*(y3 - y4) - (y1 - y2)*(x3 - x4)
                );
        final double y = (
                (y3 - y4)*(x1*y2 - x2*y1) - (y1 - y2)*(x3*y4 - x4*y3)
                ) /
                (
                (x1 - x2)*(y3 - y4) - (y1 - y2)*(x3 - x4)
                );

        return new Point2D.Double(x, y);

    }

    public static void main(String... args) {

        Graph g = new Graph(10, 0, 0, 0, 0);

        g.addValue(10, "hallo");
        g.addValue(-2000, "Valla");
        g.setMinMax();

        for (int i = 0; i < g.dataValues.length; i++) {
            if (g.dataValues[i] != null) {
                System.out.println("Index: " + i + " ,Label: " + g.dataValues[i].getLabel() + " ,Value: " + g.dataValues[i].getValue());
            }
        }

        System.out.println("Maxvalue: " + g.getMaxValue() + " ,Minvalue: " + g.getMinValue());
    }
}
