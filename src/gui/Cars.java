/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Lars Aksel
 */
public class Cars {

    private ArrayList<Car> cars;
    private Car[] collection;
    private Car[] generated;
    private int max;
    private int randFaktor;
    private Random rand;

    public Cars(int max, int randomGenerate, Car... col) {
        this.max = max;
        this.cars = new ArrayList<>();
        this.rand = new Random();
        this.randFaktor = randomGenerate;
        this.collection = col;
    }

    public void update(float delta, float windowWidth, float windowHeight) {
        for (int i = 0; i < cars.size(); i++) {
            Car c = cars.get(i);
            if (!c.update(delta, windowWidth, windowHeight)) {
                c.refresh();
            }
        }
        int r = rand.nextInt(1000);
        int i2 = rand.nextInt(collection.length);
        Car c;
        Car c2 = new Car(collection[i2]);
        if (r <= randFaktor) {
            if (cars.size() < max) {
                if (cars.isEmpty()){
                    cars.add(c2);
                }
                
                for (int i = 0; i < cars.size() && i < max; i++) {
                    c = cars.get(i);
                    boolean collide = false;
                    for (int j = 0; j < cars.size(); j++) {
                        if (i == j) {
                            continue;
                        }
                        
                        if (c2.isInside(c.getX(), c.getY(), c.getSizeX(), c.getSizeY())) {
                            collide = true;
                            break;
                        } //else System.out.println("Hallo");
                    }
                    if (!collide) {
                        cars.add(c2);
                    }
                }
            }
        }
    }

    public ArrayList<Car> getCars() {
        return this.cars;
    }
    
    /*
    public void generateCars() {
        int i2 = rand.nextInt(collection.length);
        for (int i = 0; i < max; i++) {
            cars.add(new Car(collection[i2]));
        }
    }*/
}
