package visualization;

import processing.core.PApplet;
import processing.core.PImage;

public class VisualizationSample extends PApplet {


    public static void main(String[] args) {
        PApplet.main("visualization.VisualizationSample");
    }

    @Override
    public void settings() {
        size(1000, 600);
    }

    @Override
    public void setup() {

    }

    @Override
    public void draw() {
        PImage car = loadImage("img/sportsCarR.png");
        PImage suv = loadImage("img/suvR.png");
        PImage tree = loadImage("img/tree2.png");
        PImage person = loadImage("img/person.png");
        PImage truck = loadImage("img/truck.png");
        background(255);
        drawRoad();
        image(suv, 450, 300, suv.width, suv.height);
        image(car, 250, 230, car.width * 0.8F, car.height * 0.8F);
        int treeX = 350;
        while (treeX < width) {
            image(tree, treeX, 80, 160, 120);
            treeX += 170;

        }
        image(person, 650, 290, 2 * 20, 2 * 20.22F);
        image(truck, 750, 200, truck.width, truck.height);


    }

    private void drawRoad() {
        fill(120);
        rect(0, (height / 2) - 100, 1000, 200);
        fill(250);
        int xStart = 30;
        while (xStart < width) {
            rect(xStart, (height / 2) - 5, 50, 10);
            xStart += 100;
        }

    }

}
