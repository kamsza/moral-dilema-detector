package visualization;

import generator.Model;
import org.semanticweb.owlapi.metrics.ObjectCountMetric;
import project.Entity;
import project.Lane;
import project.Living_entity;
import project.Non_living_entity;
import project.Street_crossing;
import project.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

class BottomBar extends JPanel {
    int width, height;
    Model model;

    BottomBar(int width, int height, Model model) {
        this.width = width;
        this.height = height;
        this.model = model;
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // column 1
        String time = model.getTime().toString().replace("()", "").replace("_", " ");
        String weather = model.getWeather().toString().replace("()", "").replace("_", " ");
        String speedLimit = model.getRoadType().getHas_speed_limit_kmph().iterator().next().toString();
        String mainVehicleSpeed = model.getVehicle().getSpeedY().iterator().next().toString();

        String labelStr = "<html> time: %s <br>weather: %s <br>speed limit: %s km/h <br>main vehicle speed: %s km/h";
        labelStr = String.format(labelStr, time, weather, speedLimit, mainVehicleSpeed);

        JLabel label = new JLabel(labelStr);
        label.setForeground(Color.WHITE);
        label.setBounds(20, 0, 960,140);
        label.setVerticalAlignment(JLabel.TOP);
        label.setFont(new Font(label.getName(), Font.PLAIN, 26));
        this.add(label);

        // column 2
        String labelStr2 = "<html> before main vehicle:";
        JLabel label2 = new JLabel(labelStr2);
        label2.setForeground(Color.WHITE);
        label2.setBounds(Visualization.WIDTH *2/5, 0, 960,140);
        label2.setVerticalAlignment(JLabel.TOP);
        label2.setFont(new Font(label.getName(), Font.PLAIN, 26));
        this.add(label2);

        // column 3
        String labelStr3 = "<html> <table style=\"width:300px\">" + getObjectsBeforeMainCar() + "</table>";
        JLabel label3 = new JLabel(labelStr3);
        label3.setForeground(Color.WHITE);
        label3.setBounds(Visualization.WIDTH * 3/5, 3, 960,140);
        label3.setVerticalAlignment(JLabel.TOP);
        label3.setFont(new Font(label.getName(), Font.PLAIN, 20));
        this.add(label3);
    }

    private String getObjectsBeforeMainCar() {
        Lane mainLane = model.getLanes().get(Model.Side.CENTER).get(0);
        ArrayList<Non_living_entity> objects = model.getObjects().get(mainLane);
        ArrayList<Vehicle> vehicles = model.getVehicles().get(mainLane);
        ArrayList<Living_entity> entities = model.getEntities().get(mainLane);

        TreeMap<Float, String> objectsBeforeCar = new TreeMap<>();
        objectsBeforeCar.putAll(getDistances(objects));
        objectsBeforeCar.putAll(getDistances(vehicles));
        objectsBeforeCar.putAll(getDistances(entities));

        int i = 0;
        String header = String.format("<tr><th style=\"width:150px\">%s</th>  <th style=\"width:150px\">%s</th>  <th style=\"width:150px\">%s</th></tr>", "name", "dist (m)", "speed (km/h)");
        StringBuilder out = new StringBuilder(header);
        for (Map.Entry entry : objectsBeforeCar.entrySet()) {
            if(i++ > 3)
                break;
            out.append(entry.getValue()).append("<br>");
        }

        return out.toString();
    }

    private <E extends Entity> TreeMap<Float, String> getDistances(ArrayList<E> objects) {
        TreeMap<Float, String> objectsBeforeCar = new TreeMap<>();
        for(Entity o: objects) {
            String speed = "";
            if(!o.getSpeedX().isEmpty() &&
                    !o.getSpeedY().isEmpty())
                speed = String.format("%.1f", Math.hypot(o.getSpeedX().iterator().next(), o.getSpeedY().iterator().next()));


            if(o.getDistance().iterator().next() > 0) {
                String label = String.format("<tr><th style=\"width:150px\">%s</th>  <th tyle=\"width:150px\">%.1f</th>  <th style=\"width:150px\">%s</th></tr>",
                        getObjectName(o),
                        o.getDistance().iterator().next() / 100,
                        speed);
                objectsBeforeCar.put(o.getDistance().iterator().next(), label);
            }
        }

        return objectsBeforeCar;
    }

    private String getObjectName(Object o) {
        int idx = o.getClass().getName().lastIndexOf(".");
        String objectName = o.getClass().getName().substring(idx + 1);
        objectName = objectName.replace("Default", "");
        return objectName;
    }
}