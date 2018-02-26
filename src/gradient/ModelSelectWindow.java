package gradient;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import my_components.FractalPanel;
import my_components.MyButton;

public class ModelSelectWindow extends JFrame implements WindowListener {

    static boolean exist = false;
    static ModelSelectWindow active;

    private JRadioButton r_li, r_ci, r_ln, r_ro;
    private ButtonGroup bg;
    private JPanel p_r;
    private JTextField t_pow;
    private MyButton b_ok, b_ca;
    private Listener l;
    private GraphViewer gv;
    
    private FractalPanel fp;
    private int mdl, root;

    public ModelSelectWindow(FractalPanel fp) {
        super();
        if (exist && active.getClass().equals(this.getClass())) {
            active.setSize(active.getMinimumSize());
            active.setLocationRelativeTo(null);
            active.setState(NORMAL);
            active.setVisible(true);
            active.requestFocus();
            dispose();
        } else {
            exist = true;
            active = this;
            addWindowListener(this);
            activate(fp);
        }
    }

    
    public void activate(FractalPanel fp){
        this.fp = fp;
        mdl = fp.getGradient().getModel();
        root = fp.getGradient().getRootPow();
        initW();
        initComp();
    }
    

    private void initW() {
        setTitle("Gradient Window");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //setSize(300, 250);
        setMinimumSize(new Dimension(260, 210));
        //setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        l = new Listener();
        addComponentListener(l);
    }

    private void initComp() {
        r_li = new JRadioButton("Linear");
        r_ci = new JRadioButton("Circle");
        r_ln = new JRadioButton("Log");
        r_ro = new JRadioButton("Root");
        r_li.addActionListener(l);
        r_ci.addActionListener(l);
        r_ln.addActionListener(l);
        r_ro.addActionListener(l);
        
        if(mdl == FGradient.LINEAR)
            r_li.setSelected(true);
        if(mdl == FGradient.CIRCLE)
            r_ci.setSelected(true);
        if(mdl == FGradient.LOG)
            r_ln.setSelected(true);
        if(mdl == FGradient.ROOT)
            r_ro.setSelected(true);
        
        bg = new ButtonGroup();
        bg.add(r_li);
        bg.add(r_ci);
        bg.add(r_ln);
        bg.add(r_ro);
        
        p_r = new JPanel(new GridLayout(0, 1));
        p_r.add(r_li);
        p_r.add(r_ci);
        p_r.add(r_ln);
        p_r.add(r_ro);
        
        t_pow = new JTextField("" + root);
        t_pow.setEnabled(r_ro.isSelected());
        t_pow.addActionListener(l);
        
        b_ok = new MyButton("OK");
        b_ca = new MyButton("Cancel");
        b_ok.addActionListener(l);
        b_ca.addActionListener(l);
        
        gv = new GraphViewer();
        
        p_r.setSize(70, 100);
        p_r.setLocation(10, 10);
        add(p_r);
        
        t_pow.setSize(40, 25);
        t_pow.setLocation(p_r.getWidth() + p_r.getX(), 75 + p_r.getY());
        add(t_pow);
        
        b_ok.setSize(80, 25);
        b_ok.setLocation(getContentPane().getWidth() - b_ok.getWidth() - 10, 
                getContentPane().getHeight() - b_ok.getHeight() - 10);
        add(b_ok);
        
        b_ca.setSize(80, 25);
        b_ca.setLocation(10, 
                getContentPane().getHeight() - b_ca.getHeight() - 10);
        add(b_ca);
        
        int size = getContentPane().getWidth() - 
                t_pow.getX() - t_pow.getWidth() - 20;
        if(size > b_ok.getY() - 20)
            size = b_ok.getY() - 20;
        gv.setSize(size, size);
        gv.setLocation(t_pow.getX() + t_pow.getWidth() + 10, 10);
        add(gv);
        
        repaint();
    }
    
    public void resize(){
        b_ok.setLocation(getContentPane().getWidth() - b_ok.getWidth() - 10, 
                getContentPane().getHeight() - b_ok.getHeight() - 10);
        
        b_ca.setLocation(10, 
                getContentPane().getHeight() - b_ca.getHeight() - 10);
        
        int size = getContentPane().getWidth() - 
                t_pow.getX() - t_pow.getWidth() - 20;
        if(size > b_ok.getY() - 20)
            size = b_ok.getY() - 20;
        gv.setSize(size, size);
    }

    private class GraphViewer extends JButton{
        private final Color BACK_COLOR = Color.WHITE, 
                GRAPH_COLOR = Color.BLACK, AXIS_COLOR = Color.LIGHT_GRAY;
        
        @Override
        public void paint(Graphics g){
            int w = getWidth(), h = getHeight();
            g.setColor(BACK_COLOR);
            g.fillRect(0, 0, w, h);
            g.setColor(AXIS_COLOR);
            g.drawLine(0, 0, 0, h);
            g.drawLine(0, 0, w, 0);
            g.drawLine(0, h-1, w-1, h-1);
            g.drawLine(w-1, 0, w-1, h-1);
            g.setColor(GRAPH_COLOR);
            for(int i = 0; i < w-1; i++){
                g.drawLine(i, h - (int)(h*FGradient.f((double)i/w, mdl, root)), 
                        i+1, h - (int)(h*FGradient.f((double)(i+1)/w, mdl, root)));
            }
        }
    }
    
    private class Listener implements ActionListener, ComponentListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == r_li){
                mdl = FGradient.LINEAR;
                t_pow.setEnabled(false);
                repaint();
            }
            if(e.getSource() == r_ci){
                mdl = FGradient.CIRCLE;
                t_pow.setEnabled(false);
                repaint();
            }
            if(e.getSource() == r_ln){
                mdl = FGradient.LOG;
                t_pow.setEnabled(false);
                repaint();
            }
            if(e.getSource() == r_ro){
                mdl = FGradient.ROOT;
                t_pow.setEnabled(true);
                repaint();
            }
            
            if(e.getSource() == t_pow){
                synchRoot();
                fp.getGradient().setModel(mdl);
                fp.getGradient().setRootPow(root);
                fp.repaint();
                repaint();
            }
            
            if(e.getSource() == b_ca){
                mdl = fp.getGradient().getModel();
                root = fp.getGradient().getRootPow();
                if (mdl == FGradient.LINEAR) {
                    r_li.setSelected(true);
                }
                if (mdl == FGradient.CIRCLE) {
                    r_ci.setSelected(true);
                }
                if (mdl == FGradient.LOG) {
                    r_ln.setSelected(true);
                }
                if (mdl == FGradient.ROOT) {
                    r_ro.setSelected(true);
                }
                t_pow.setEnabled(r_ro.isSelected());
                repaint();
            }
            if(e.getSource() == b_ok){
                synchRoot();
                fp.getGradient().setModel(mdl);
                fp.getGradient().setRootPow(root);
                fp.repaint();
            }
        }
        
        public void synchRoot(){
            try{
                if(t_pow.getText().equals(""))
                    return;
                int i = Integer.parseInt(t_pow.getText());
                if(i < 1){
                    i = 1;
                    t_pow.setText("" + i);
                    t_pow.setCaretPosition(1);
                }else if(i > 10){
                    i = 10;
                    t_pow.setText("" + i);
                    t_pow.setCaretPosition(2);
                }
                root = i;
                repaint();
            } catch(NumberFormatException ne){
                t_pow.setText("" + root);
            } catch(NullPointerException ne) {
                
            }
        }

        @Override
        public void componentResized(ComponentEvent e) {
            resize();
        }

        @Override
        public void componentMoved(ComponentEvent ce) {
        }

        @Override
        public void componentShown(ComponentEvent ce) {
        }

        @Override
        public void componentHidden(ComponentEvent ce) {
        }
    }

    @Override
    public void windowOpened(WindowEvent we) {
    }

    @Override
    public void windowClosing(WindowEvent we) {
        exist = false;
        active = null;
        dispose();
    }

    @Override
    public void windowClosed(WindowEvent we) {
    }

    @Override
    public void windowIconified(WindowEvent we) {
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
    }

    @Override
    public void windowActivated(WindowEvent we) {
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
    }
}
