package parallel;

import gradient.FGradient;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import my_components.FractalPanel;

public class ThreadManager implements Runnable {

    public static final int TH_MAX = 1000;

    private int th = 10;
    private FractalThread threads[];
    private Thread mainTh;

    private FractalPanel fp;
    private FGradient fg;
    private boolean working, started = false;

    public ThreadManager(FractalPanel fp) {
        this.fp = fp;

        threads = new FractalThread[TH_MAX];
        for (int i = 0; i < TH_MAX; i++) {
            threads[i] = new FractalThread();
        }
        mainTh = new Thread(this){
            boolean st = false;
            @Override
            public void start(){
                if(!st){
                    super.start();
                    st = true;
                }
            }
        };
    }

    public void reCalculate() {
        working = true;
        mainTh.start();
    }

    @Override
    public void run() {
        while (true) {
            if(working){
                int w = fp.getWidth(); 
                int h = fp.getHeight();
                double X = fp.getFX();
                double Y = fp.getFY();
                double scale = fp.getScale();
                int N = fp.getItr();
                fg = fp.getGradient();
                BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                int i = 0;
                for (i = 0; i < th; i++) {
                    threads[i].calculate(i % w, i / w, X, Y, scale, N, w, h);
                    if(!started){
                        threads[i].start();
                    }
                }
                started = true;
                System.err.println("123");
                while (i < w * h) {
                    for (int j = 0; j < th; j++) {
                        if (threads[j].isReady()) {
                            //bi.setRGB(threads[j].getX(), threads[j].getY(), fp.getGradient().getColor(threads[j].getC(), N - 1).getRGB());
                            bi.setRGB(threads[j].getX(), threads[j].getY(), threads[j].getC());
                            //fp.repaint();
                            threads[j].calculate(i % w, i / w,  X, Y, scale, N, w, h);
                            i++;
                        }
                    }
                    fp.setMatrix(bi);
                    fp.repaint();
                }
                System.err.println("456");
                fp.setMatrix(bi);
                fp.repaint();
                working = false;
            }
            /*try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {}*/
        }
    }

    public class FractalThread extends Thread {

        private boolean ready, working;
        private int x, y;
        private int c;
        
        private double X, Y, scale;
        private int N, w, h;

        public FractalThread() {
            super();
            ready = true;
        }

        public boolean isReady() {
            return ready;
        }
        
        public int getC(){
            return c;
        }
        
        public int getX(){
            return x;
        }
        
        public int getY(){
            return y;
        }

        public void calculate(int x, int y, double X, double Y, 
                double scale, int N, int w, int h) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.X = X;
            this.Y = Y;
            this.scale = scale;
            this.N = N;
            ready = false;
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
        public void run() {
            System.out.println("started");
            while (true) {
                if (!ready) {
                    //System.out.println("begin");
                    /*c = fp.getGradient().getColor(isIn(
                            x / scale - X -w/2/scale, 
                            y / scale - Y -h/2/scale),
                            fp.getItr() - 1).getRGB();*/
                    
                    c = isIn(x / scale - X -w/2/scale, 
                            y / scale - Y -h/2/scale);
                    c = fg.getColor(c, N - 1).getRGB();
                    
                    //working = false;
                    ready = true;
                    /*try {
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {}*/
                    //System.out.println("ready: " + x + "; "+ y + ": " + c);
                }
            }
        }
    }
}
