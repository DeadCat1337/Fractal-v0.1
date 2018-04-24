package parallel;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import my_components.FractalPanel;

public class ThreadManager implements Runnable {

    public static final int TH_MAX = 1000;

    private int th = 1;
    private FractalThread threads[];
    private Thread mainTh;
    
    private double X, Y, scale;
    private int N, w, h;

    private FractalPanel fp;
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
                w = fp.getWidth(); 
                h = fp.getHeight();
                X = fp.getFX();
                Y = fp.getFY();
                scale = fp.getScale();
                N = fp.getItr();
                int i;
                for (i = 0; i < th; i++) {
                    if(!started){
                        threads[i].start();
                    }
                    threads[i].calculate(i % w, i / w);
                }
                started = true;
                System.err.println("123");
                while (i < w * h) {
                    for (int j = 0; j < th; j++) {
                        if (threads[j].isReady()) {
                            fp.getMatrix().setRGB(threads[j].getX(), threads[j].getY(), threads[j].getC());
                            threads[j].calculate(i % w, i / w);
                            i++;
                        }
                    }
                    fp.repaint();
                }
                System.err.println("456");
                //fp.repaint();
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {}
        }
    }

    public class FractalThread extends Thread {

        private boolean ready, working;
        private int x, y;
        private int c;

        public FractalThread() {
            super();
            ready = false;
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

        public void calculate(int x, int y) {
            ready = false;
            working = false;
            this.x = x;
            this.y = y;
            working = true;
        }

        public int isIn(double X, double Y) {
            double a = X, b = Y, j;
            for (int i = 0; i < fp.getItr(); i++) {
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
                if (working) {
                    //System.out.println("begin");
                    c = fp.getGradient().getColor(isIn(
                            x / scale - X -w/2/scale, 
                            y / scale - Y -h/2/scale),
                            fp.getItr() - 1).getRGB();
                    //fp.getMatrix().setRGB(x, y, c.getRGB());
                    ready = true;
                    working = false;
                    /*try {
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {}*/
                    fp.repaint();
                    //System.out.println("ready: " + x + "; "+ y);
                }
                
            }
        }
    }

}
