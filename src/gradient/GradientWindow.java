package gradient;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import my_components.FractalPanel;


public class GradientWindow extends JFrame implements WindowListener{
    private FractalPanel fp;
    private FGradient fg;
    
    
    public GradientWindow(FractalPanel fp){
        
        super();
        this.fp = fp;
        activate();
        addWindowListener(this);
    }
    
    
    
    
    
    
    
    
    public void activate()
    {
        setTitle("Gradient Window");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setExtendedState(JFrame.NORMAL);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(null);
        setVisible(true);
        requestFocus();
        
        
        //fp.setGradient(new FGradient());
        
        /*final double r = Math.random(), g = Math.random(), b = Math.random();
        fp.setGradient(new FGradient() {
            @Override
            public Color getColor(int i, int max) {
                if (i == -1) {
                    return new Color(255, 255, 255);
                } else {
                    return new Color((int) (r * 255 * (double) i / max), 
                            (int) (g * 255 * (double) i / max), 
                            (int) (b * 255 * (double) i / max));
                }
            }
        });*/
        //setVisible(false);
    }

    @Override
    public void windowOpened(WindowEvent we) {
    }

    @Override
    public void windowClosing(WindowEvent we) {
        fp.requestFocus();
        setVisible(false);
    }

    @Override
    public void windowClosed(WindowEvent we) {
    }

    @Override
    public void windowIconified(WindowEvent we) {
        fp.requestFocus();
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
    }

    @Override
    public void windowActivated(WindowEvent we) {
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
        //fp.requestFocus();
    }
}
