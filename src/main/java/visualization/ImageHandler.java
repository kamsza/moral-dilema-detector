package visualization;

import project.Entity;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

class ImageHandler {
    /**
     * Returns .png image as BufferedImage from resources/img
     * @name name of a file in format name.png
     */
    public static BufferedImage getImage(String name) {
        String currentDirectory = System.getProperty("user.dir");
        String filePath = currentDirectory + "/src/main/resources/img/" + name + ".png";
        BufferedImage image = null;

        try {
            File imageFile = new File(filePath);

            if(imageFile.exists())
                image = ImageIO.read(imageFile);
            else {
                String defaultFilePath = currentDirectory + "/src/main/resources/img/no_image.png";
                File defaultImageFile = new File(defaultFilePath);
                image = ImageIO.read(defaultImageFile);
                System.out.println("VISUALIZER: no image for: " + name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    /**
     * Returns .png image as BufferedImage from resources/img
     * that has the same name as class of given object
     * or img/no_image.png if such image don't exists
     */
    public static BufferedImage getImage(Entity e) {
        String classFullName = e.getClass().getName();
        int lastIdxDot = classFullName.lastIndexOf('.');
        String className = classFullName.substring(lastIdxDot + 1).replace("Default", "");

        BufferedImage image =  getImage(className);

        Iterator speedXit = e.getSpeedX().iterator();
        Iterator speedYit = e.getSpeedY().iterator();
        if(speedXit.hasNext() && speedYit.hasNext()) {
            double speedX =  Double.parseDouble(speedXit.next().toString());
            double speedY =  Double.parseDouble(speedYit.next().toString());
            double angle = Math.atan2(speedY, speedX);
            image = rotateImage(image, -1*angle);
        }
        return image;
    }

    /**
     * Returns .png image as BufferedImage from resources/img
     * that has the same name as class of given object
     * or img/no_image.png if such image don't exists
     * resize image to match new dimensions - scale
     * parameter is a percent by which old dimensions should be changed
     */
    public static BufferedImage getImage(Entity e, double scale) {
        BufferedImage img = ImageHandler.getImage(e);

        int newHeight = (int)(scale * img.getHeight());
        int newWidth = (int)(scale * img.getWidth());

        Image tmp = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    private static BufferedImage rotateImage(BufferedImage image, double angle) {
        if(angle == 0)
            return image;
        double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
        int w = image.getWidth(), h = image.getHeight();
        int neww = (int)Math.floor(w*cos+h*sin), newh = (int) Math.floor(h * cos + w * sin);
        GraphicsConfiguration gc = getDefaultConfiguration();
        BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
        Graphics2D g = result.createGraphics();
        g.translate((neww - w) / 2, (newh - h) / 2);
        g.rotate(angle, w / 2, h / 2);
        g.drawRenderedImage(image, null);
        g.dispose();
        return result;
    }

    private static GraphicsConfiguration getDefaultConfiguration() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        return gd.getDefaultConfiguration();
    }

    /**
     * Function exports JPanel as .png image file in format: vis__dd_MM_yyyy__HH_mm_ss
     * (where dd_MM_yyyy__HH_mm_ss is current date and time) to resources/vis_out directory
     */
    public static void saveImage(JPanel p) throws IOException {
        BufferedImage img = new BufferedImage(p.getWidth(), p.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        p.paint(g2d);
        g2d.dispose();
        String currentDirectory = System.getProperty("user.dir");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_yyyy__HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();
        String currentTime = dtf.format(now);
        String filename = "vis__" + currentTime + ".png";

        String filePath = currentDirectory + "/src/main/resources/vis_out/" + filename;
        int counter = 1;
        while(Files.exists(Paths.get(filePath))) {
            filename = "vis__" + currentTime + "_" + counter + ".png";
            filePath = currentDirectory + "/src/main/resources/vis_out/" + filename;
            counter++;
        }

        File newFile = new File(filePath);
        newFile.mkdirs();
        ImageIO.write(img, "png", newFile);
    }
}