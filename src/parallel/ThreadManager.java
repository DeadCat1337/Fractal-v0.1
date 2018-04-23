package parallel;

import my_components.FractalPanel;

public class ThreadManager {
    public static final int TH_MAX = 100;
    
    private int th = 10;
    private Thread threads[];
    
    private FractalPanel fp;
    
    
    public ThreadManager(FractalPanel fp){
        this.fp = fp;
        
        threads = new Thread[TH_MAX];
        for(Thread thi : threads){
            thi = new Thread(new FractalThread());
            thi.start();
        }
    }
    
    public class FractalThread implements Runnable{
        
        public FractalThread(){
            
        }
        
        @Override
        public void run() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
        
    }
    
}
