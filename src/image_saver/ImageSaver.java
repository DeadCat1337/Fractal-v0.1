package image_saver;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import my_components.FractalPanel;


public class ImageSaver implements Runnable{
    
    BufferedImage bi;
    File f;
    FractalPanel fp;
    boolean ready = false;
    
    public ImageSaver(FractalPanel fp){
        this.fp = fp;
        try{
            bi = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB);
            //f = new File(System.getProperty("user.home") + "\\Рабочий стол\\1.png");
            f = new File("1.png");
            f.createNewFile();
            Thread t = new Thread(this);
            t.start();
            System.out.println("image_saver.ImageSaver.<init>()");
            ImageIO.write(bi, "png", f);
        } catch(IOException ex) {
            System.out.println("LOL");
        }
    }

    @Override
    public void run() {
        for(int i = 0; i < bi.getWidth(); i++){
            for(int j = 0; j < bi.getHeight(); j++){
                bi.setRGB(i, j, fp.getGradient().getColor(
                        fp.isIn(i / fp.getScale() - fp.getFX() - 
                        bi.getWidth()/2/fp.getScale(), 
                        j / fp.getScale() - fp.getFY() - 
                        bi.getHeight()/2/fp.getScale()), fp.getItr() - 1).getRGB());
                //bi.setRGB(i, j, Color.RED.getRGB());
            }
        }
        ready = true;
        System.err.println("Kawodpj" + ready);
    }
}
