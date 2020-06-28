package visualization;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class ImageHandler {
    /**
     * Returns .png image as BufferedImage from resources/img
     * that has the same name as class of given object
     * or img/no_image.png if such image don't exists
     */
    public static BufferedImage getImage(Object o) {
        String classFullName = o.getClass().getName();
        int lastIdxDot = classFullName.lastIndexOf('.');
        String className = classFullName.substring(lastIdxDot + 1).replace("Default", "");

        String currentDirectory = System.getProperty("user.dir");
        String filePath = currentDirectory + "/src/main/resources/img/" + className + ".png";
         System.out.println("FILE PAH: " + filePath);
        BufferedImage image = null;

        try {
            File imageFile = new File(filePath);

            if(imageFile.exists())
                image = ImageIO.read(imageFile);
            else {
                String defaultFilePath = currentDirectory + "/src/main/resources/img/no_image.png";
                File defaultImageFile = new File(defaultFilePath);
                image = ImageIO.read(defaultImageFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
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

        String filePath = currentDirectory + "/src/main/resources/vis_out/vis__" + currentTime + ".png";
        ImageIO.write(img, "png", new File(filePath));
    }
}
