package Arkanoid.graphic.util;

import Arkanoid.Logic.models.Ball;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.HashMap;

public class ImageLoader {
    private static HashMap<String, Image> cache = new HashMap<>();

    public static Image getImage(String adder, int w, int h) {
        File path = new File("./resources/" + adder);
        if (!path.exists())
            return null;
        if (cache.containsKey(adder))
            return cache.get(adder).getScaledInstance(w, h, Image.SCALE_DEFAULT);
        try {
            Image img = ImageIO.read(path);
            cache.put(adder, img);
            return img.getScaledInstance(w, h, Image.SCALE_DEFAULT);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
