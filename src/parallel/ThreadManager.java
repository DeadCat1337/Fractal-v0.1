package parallel;

import gradient.FGradient;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import my_components.FractalPanel;

public class ThreadManager {

    public static final int TH_MAX = 2000;

    private int th = 1000;

    private FractalPanel fp;
    private FGradient fg;
    ExecutorService es, mn;

    public ThreadManager(FractalPanel fp) {
        this.fp = fp;
        if (th > TH_MAX) {
            th = TH_MAX;
        }
        mn = Executors.newFixedThreadPool(1);
    }

    public void reCalculate() {
        try {
            mn.shutdown();
            mn.awaitTermination(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        mn = Executors.newFixedThreadPool(1);
        /*Future<BufferedImage> f = mn.submit(new Callable<BufferedImage>() {
        @Override
        public BufferedImage call() throws Exception {
        return create();
        }
        });
        try {
        fp.setMatrix((BufferedImage) f.get());
        } catch (InterruptedException | ExecutionException ex) {
        Logger.getLogger(ThreadManager.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        mn.submit(new Runnable() {
            @Override
            public void run() {
                create();
            }
        });
        System.out.println("LOL1");
    }

    public BufferedImage create() {
        System.out.println("LOL2");
        int w = fp.getWidth();
        int h = fp.getHeight();
        double X = fp.getFX();
        double Y = fp.getFY();
        double scale = fp.getScale();
        int N = fp.getItr();
        fg = fp.getGradient();
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        es = Executors.newWorkStealingPool();
        ArrayList<FractalThread> ls = new ArrayList<>();

        try {
            int i;
            for (i = 0; i < w*h; i++) {
                ls.add(new FractalThread(i, X, Y, scale, N, w, h, bi));
            }
            //System.out.println("LOL2.5");
            es.invokeAll(ls);

            fp.setMatrix(bi);
            fp.repaint();
            /*int j = 2;
            while (i < w * h) {
                for (; i < th*j; i++) {
                    ls.get(i - th*(j-1)).setPoint(i);
                }
                //System.out.println("LOL2.5");
                es.invokeAll(ls);
                fp.setMatrix(bi);
                fp.repaint();
                j++;
                //System.err.println(":" + j);
            }*/
            es.shutdown();
            es.awaitTermination(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        fp.setMatrix(bi);
        fp.repaint();
        System.out.println("LOL3");
        return bi;
    }

    public class FractalThread implements Callable<Integer> {

        private int x, y;
        private int c;

        //private double X, Y, scale;
        //private int N, w, h;
        //private BufferedImage bi;

        public FractalThread(int i, double X, double Y,
                double scale, int N, int w, int h, BufferedImage bi) {

            this.w = w;
            this.h = h;
            this.X = X;
            this.Y = Y;
            this.scale = scale;
            this.N = N;
            this.bi = bi;

            x = i % w;
            y = i / h;
            //System.out.println("ready:" + i);
        }
        
        public void setPoint(int i){
            x = i % w;
            y = i / h;
        }

        public int isIn(double X, double Y) {
            double a = X, b = Y, j;
            for (int i = 0; i < N; i++) {
                j = a;

                a = a * a - b * b + X;
                b = 2 * j * b + Y;

                if (a * a + b * b > 4) {
                    return i;
                }
            }
            return -1;
        }

        @Override
        public Integer call() throws Exception {
            //System.out.println("begin");
            /*c = fp.getGradient().getColor(isIn(
                            x / scale - X -w/2/scale, 
                            y / scale - Y -h/2/scale),
                            fp.getItr() - 1).getRGB();*/

            c = isIn(x / scale - X - w / 2 / scale,
                    y / scale - Y - h / 2 / scale);
            c = fg.getColor(c, N - 1).getRGB();
            bi.setRGB(x, y, c);
            return c;
        }
    }
}
