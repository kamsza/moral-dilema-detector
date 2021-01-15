package visualization;

import generator.Model;
import generator.RoadModel;
import generator.SizeManager;
import project.Surrounding;
import project.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

class SurroundingPanel extends JPanel {
    int width, height;
    Model model;
    Model.Side side;

    SurroundingPanel(int width, int height, Model model, Model.Side side) {
        this.width = width;
        this.height = height;
        this.side = side;
        this.model = model;

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        ArrayList<Surrounding> surrounding = model.getSurrounding().get(side);

        for (Surrounding s : surrounding) {
            BufferedImage surrImg = ImageHandler.getImage(s);

            int dist = (int) (0.01 * s.getDistance().iterator().next() * SizeManager.METERS_TO_PX);
            int length = (int) (0.01 * s.getLength().iterator().next() * SizeManager.METERS_TO_PX);
            int width = surrImg.getHeight();

            int x = dist - length / 2 + Visualization.WIDTH / 2;
            int y = side == Model.Side.RIGHT ? 20 : height - 20 - surrImg.getHeight();

            drawImage(g, surrImg, x, y, length, width);
        }

        if (hasRoadOnSide()) {
            drawRoadOnSide(g);
        }

    }

    private void drawRoadOnSide(Graphics g) {
        // draw junction road
        int sideRoadStartPoint = 820;
        int sideRoadWidth = 140;
        g.setColor(Color.GRAY);
        g.fillRect(sideRoadStartPoint, 0, sideRoadWidth, this.height + 2);
        g.setColor(Color.WHITE);
        for (int y = 10; y < height; y += 60) {
            g.fillRect(sideRoadStartPoint + (sideRoadWidth / 2) - 3, y, 6, 25);
        }

        RoadModel roadModel = getRoadOnSide().get(0);
        ArrayList<Vehicle> vehicleArrayList = new ArrayList<>();
        Collection<ArrayList<Vehicle>> values = roadModel.getVehicles().values();
        for (ArrayList<Vehicle> vehicles : values) {
            vehicleArrayList.addAll(vehicles);
        }

        Random random = new Random();
        int carNum = vehicleArrayList.size();
        String[] directions = {"Up", "Down"};
        for (int i = 0; i < carNum; i++) {
            String direction = directions[i];
            int x = direction.equals("Up") ? (sideRoadStartPoint + sideRoadWidth / 2 + 5) : (sideRoadStartPoint + 5);
            int y = random.nextInt(100);

            String carType = "suv";
            switch (random.nextInt(3)) {
                case 0:
                    carType = "suv";
                    break;
                case 1:
                    carType = "sportsCar";
                    break;
                case 2:
                    carType = "truck";
                    break;
            }

            BufferedImage carImg = ImageHandler.getImage(carType + direction);
            g.drawImage(carImg, x, y, this);
        }

    }

    private void drawImage(Graphics g, BufferedImage img, int x, int y, int length, int width) {
        if (hasRoadOnSide()) {
            if (x > 700) return;
            if (x + length > 700) {
                length = 700 - x;
            }
        }
        int imgLength = Math.min(img.getWidth(), length);
        int imgHeight = Math.min(img.getHeight(), width);

        if (x != 0) x += 10;
        if (length != Visualization.WIDTH) length -= 10;

        for (int x1 = x; x1 < x + length; x1 += imgLength) {
            if (x1 > Visualization.WIDTH)
                break;
            int x2 = Math.min(x1 + imgLength, x + length);
            int y2 = y + imgHeight;
            g.drawImage(img, x1, y, x2, y2, 0, 0, imgLength, imgHeight, this);
        }
    }

    private boolean hasRoadOnSide() {
        float angle = 0;
        if (this.side == Model.Side.LEFT) {
            angle = 0F;
        } else if (this.side == Model.Side.RIGHT) {
            angle = 180F;
        }
        float finalAngle = angle;
        return !model.getOtherRoads().stream()
                .map(r -> r.getRoad())
                .filter(r -> r.hasStart_angle() && (r.getStart_angle().iterator().next() == finalAngle))
                .collect(Collectors.toList())
                .isEmpty();
    }

    private List<RoadModel> getRoadOnSide() {
        float angle = 0;
        if (this.side == Model.Side.LEFT) {
            angle = 0F;
        } else if (this.side == Model.Side.RIGHT) {
            angle = 180F;
        }
        float finalAngle = angle;
        return model.getOtherRoads().stream()
                .filter(r -> r.getRoad().hasStart_angle() && (r.getRoad().getStart_angle().iterator().next() == finalAngle))
                .collect(Collectors.toList());
    }
}